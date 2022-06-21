package com.gb.chat.fx.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientChatApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientChatApp.class.getResource("chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 350);
        stage.setTitle("Authentication form");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}