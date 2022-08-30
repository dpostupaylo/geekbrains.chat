package com.gb.chat.fx.client;

import com.gb.chat.fx.client.logger.FileLogger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import com.gb.chat.fx.client.logger.Logger;


public class Client {
    private final int DEFAULT_COUNT = 100;
    private Logger logger;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private ClientController clientController;

    public Client(ClientController controller){
        this.clientController = controller;
        this.logger = new FileLogger();
    }

    public void openConnection(String login, String password) {
        try {
            socket = new Socket("0.0.0.0", 8181);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("/auth " + login + " " + password);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String strFromServer = in.readUTF();
                            if(strFromServer.startsWith("/authok")) {
                                clientController.setAuth(true);
                                String history = logger.loadHistory(login, DEFAULT_COUNT);
                                clientController.addMessageInMessageArea(history);
                                break;
                            }
                            clientController.addMessageInMessageArea(strFromServer + "\n");
                            logger.log(login, strFromServer + "\n");
                        }
                        while (true) {
                            String strFromServer = in.readUTF();
                            if (strFromServer.equalsIgnoreCase("/end")) {
                                break;
                            }
                            clientController.addMessageInMessageArea(strFromServer);
                            logger.log(login, strFromServer + "\n");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
