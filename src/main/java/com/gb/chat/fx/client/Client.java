package com.gb.chat.fx.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private ClientController clientController;

    public Client(ClientController controller){
        this.clientController = controller;
    }

    public void openConnection(String login, String password) {
        try {
            socket = new Socket("localhost", 8189);
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
                                break;
                            }
                            clientController.addMessageInMessageArea(strFromServer + "\n");
                        }
                        while (true) {
                            String strFromServer = in.readUTF();
                            if (strFromServer.equalsIgnoreCase("/end")) {
                                break;
                            }
                            clientController.addMessageInMessageArea(strFromServer);
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
