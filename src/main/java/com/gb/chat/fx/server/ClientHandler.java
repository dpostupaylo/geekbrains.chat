package com.gb.chat.fx.server;

import com.gb.chat.fx.server.db.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;


public class ClientHandler implements Runnable {

    private Logger logger = Logger.getLogger(ClientHandler.class);

    private Server myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private User user;
    public String getName() {
        return user.getNick();
    }

    public ClientHandler(Server myServer, Socket socket) {
        try {
            logger.info("initializing new client handler");
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Error during ClientHandler creating");
        }
    }

    @Override
    public void run() {
        try {
            authentication();
            readMessages();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void authentication() throws IOException {
        while (true) {
            String str = in.readUTF();
            logger.info(String.format("New message %s", str));
            if (str.startsWith("/auth")) {
                String[] parts = str.split("\\s");
                user = myServer.getAuthService().getUserByLoginPass(parts[1], parts[2]);
                if (user != null) {
                    if (!myServer.isNickBusy(user.getNick())) {
                        sendMsg("/authok " + user.getNick());
                        myServer.broadcastMsg(user.getNick() + " entered to chat");
                        myServer.subscribe(this);
                        return;
                    } else {
                        sendMsg("Account is used by somebody");
                    }
                } else {
                    sendMsg("Wrong login/password");
                }
            }
        }
    }

    public void readMessages() throws IOException {
        while (true) {
            String strFromClient = in.readUTF();
            logger.info(String.format("from %s : %s", user.getNick(), strFromClient));
            if (strFromClient.equals("/end")) {
                return;
            }
            if (strFromClient.startsWith("/w")) {
                String[] messages = strFromClient.split("\\s");
                String nick = messages[1];
                String message = Arrays.stream(Arrays.copyOfRange(messages, 2, messages.length)).collect(Collectors.joining(" "));
                myServer.sendToSomebody(user.getNick(), nick, String.format("%s to %s : %s",user.getNick(), nick, message));
            } else if (strFromClient.startsWith("/nickamend")) {
                String[] messages = strFromClient.split("\\s");
                String nick = messages[1];
                myServer.getAuthService().updateUserNick(user, nick);
                myServer.broadcastMsg(user.getNick() + ": nick amend " + user.getNick() + "->" + nick);
                this.user = myServer.getAuthService().getUserByLoginPass(user.getLogin(), user.getPass());
            } else {
                myServer.broadcastMsg(user.getNick() + ": " + strFromClient);
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMsg(user.getNick() + " out of chat");
        logger.info(user.getNick() + " out of chat");
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
