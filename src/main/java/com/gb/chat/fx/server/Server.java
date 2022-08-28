package com.gb.chat.fx.server;

import com.gb.chat.fx.auth.AuthService;
import com.gb.chat.fx.auth.BaseAuthService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final int PORT = 8181;

    private List<ClientHandler> clients;
    private AuthService authService;

    public static void main(String[] args) {
        new Server();
    }

    public AuthService getAuthService() {
        return authService;
    }

    public Server() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                System.out.println("Server is waiting for clients");
                Socket socket = server.accept();
                System.out.println("Client connected");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println("Error during server work");
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public void sendToSomebody(String nameFrom, String nameTo, String message){
        System.out.println("From " +nameFrom+ " to " + nameTo + " " + message);
        clients.stream().filter(it -> it.getName().equals(nameTo) || it.getName().equals(nameFrom)).forEach(it -> it.sendMsg(message));
    }

    public synchronized void broadcastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    public synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
    }

    public synchronized void subscribe(ClientHandler o) {
        clients.add(o);
    }
}
