package com.example.geekbrains.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;

    private DataInputStream in;
    private DataOutputStream out;

    public static void main(String[] args) {
        new Server().run();
    }

    public void run() {
        try {
            launchServer();
            launchReader();
            launchWriter();
            Thread.currentThread().join();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void launchReader(){
        new Thread(() -> {
            try {
                while (true) {
                    final String message = in.readUTF();
                    System.out.println(String.format("Message from server %s", message));
                    if ("/end".equalsIgnoreCase(message)) {
                        break;
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } finally {
                closeConnection();
            }
        }).start();
    }

    private void launchWriter(){
        new Thread(() -> {
            try {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    out.writeUTF(scanner.nextLine());
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } finally {
                closeConnection();
            }
        }).start();
    }

    private void sendMessage(String message){
        try{
            out.writeUTF(message);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void launchServer() throws IOException {
        serverSocket = new ServerSocket(8080);
        System.out.println("Server started!");
        socket = serverSocket.accept();
        System.out.println("Client connected!");
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        new Thread( () -> {
            try{
                while(true){
                    final String message = in.readUTF();
                    System.out.println(String.format("Message from client %s",message));
                    if ("/end".equalsIgnoreCase(message)){
                        break;
                    }
                }
            }catch (IOException ex){
                System.out.println(ex.getMessage());
            } finally {
                closeConnection();
            }
        }).start();
    }

    private void closeConnection(){
        this.close(in);
        this.close(out);
        this.close(socket);
    }

    private void close(Closeable object){
        if (object != null){
            try{
                object.close();
            } catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}
