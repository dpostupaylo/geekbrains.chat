package com.example.geekbrains.client;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public static void main(String[] args) {
        new Client().run();
    }

    private void run() {
        try {
            openConnection();
            launchReader();
            launchWriter();
        } catch (IOException ex) {
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

    private void openConnection() throws IOException {
        socket = new Socket("127.0.0.1", 8080);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    private void closeConnection() {
        this.close(in);
        this.close(out);
        this.close(socket);
    }

    private void close(Closeable object) {
        if (object != null) {
            try {
                object.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
