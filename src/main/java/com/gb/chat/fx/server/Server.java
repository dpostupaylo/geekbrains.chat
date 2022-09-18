package com.gb.chat.fx.server;

import com.gb.chat.fx.auth.AuthService;
import com.gb.chat.fx.auth.BaseAuthService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;


public class Server {
    private Logger logger = Logger.getLogger(Server.class);

    private final int PORT = 8181;

    private ExecutorService handlers = Executors.newCachedThreadPool();

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
                logger.info("Server is waiting for clients");
                Socket socket = server.accept();
                logger.info("Client connected");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            logger.error("Error during server work");
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
        logger.info(String.format("From %s to %s - %s", nameFrom, nameTo, message));
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
        handlers.submit(o);
        clients.add(o);
    }
}
