package com.gb.chat.fx.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ClientHandler {
    private Server myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private Thread authTimeoutCounter;
    private boolean isActive = false;

    public String getName() {
        return name;
    }

    public ClientHandler(Server myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            this.authTimeoutCounter = new Thread(() -> {
                try {
                    Thread.sleep(120_000);

                    if (!isActive) {
                        myServer.broadcastMsg("New user is authenticated");
                        this.closeConnection();
                    }
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            });
            this.authTimeoutCounter.start();

            new Thread(() -> {
                try {
                    readMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            });

        } catch (IOException e) {
            throw new RuntimeException("Error during ClientHandler creating");
        }
    }

    private void authentication(String strFromClient){
        String[] parts = strFromClient.split("\\s");
        String nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
        if (nick != null) {
            if (!myServer.isNickBusy(nick)) {
                sendMsg("/authok " + nick);
                name = nick;
                myServer.broadcastMsg(name + " entered to chat");
                myServer.subscribe(this);
                isActive = true;
                return;
            } else {
                sendMsg("Account is used by somebody");
            }
        } else {
            sendMsg("Wrong login/password");
        }
    }

    public void readMessages() throws IOException {
        while (true) {
            String strFromClient = in.readUTF();

            if (strFromClient.equals("/end")) {
                return;
            }

            if (strFromClient.startsWith("/new")){
                myServer.broadcastMsg("new user is coming");
                continue;
            }

            if (strFromClient.startsWith("/auth")){
                authentication(strFromClient);
                continue;
            }

            System.out.println("from " + name + ": " + strFromClient);

            if (strFromClient.startsWith("/w")) {
                String[] messages = strFromClient.split("\\s");
                String nick = messages[1];
                String message = Arrays.stream(Arrays.copyOfRange(messages, 2, messages.length)).collect(Collectors.joining(" "));
                myServer.sendToSomebody(name, nick, String.format("%s to %s : %s", name, nick, message));
            } else {
                myServer.broadcastMsg(name + ": " + strFromClient);
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

        if (!this.socket.isClosed()) {
            myServer.unsubscribe(this);
            myServer.broadcastMsg(name + " out of chat");
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
}
