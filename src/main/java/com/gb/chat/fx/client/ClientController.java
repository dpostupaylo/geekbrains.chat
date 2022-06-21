package com.gb.chat.fx.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class ClientController {

    private Client client;

    @FXML
    public VBox chatForm;
    @FXML
    public VBox authForm;
    @FXML
    public TextField password;
    @FXML
    public TextField login;
    @FXML
    private TextArea conversationArea;
    @FXML
    private TextField messageInput;

    @FXML
    public void btnOneClickAction(ActionEvent actionEvent) {
        sendMessage();
    }

    @FXML
    public void msgAreaClickAction(KeyEvent keyEvent){
        if (keyEvent.getCode() == KeyCode.ENTER)
            sendMessage();
    }

    public ClientController(){
        this.client = new Client(this);
    }

    private void sendMessage(){
        client.sendMessage(messageInput.getText());
        messageInput.clear();
    }

    public void addMessageInMessageArea(String message){
        conversationArea.appendText(String.format("%s\n", message));
    }

    public void btnAuthenticationAction(ActionEvent actionEvent) {
        if (login.getText().isEmpty()){
            System.out.println("Please input login");
            return;
        }

        if (password.getText().isEmpty()){
            System.out.println("Please input password");
            return;
        }

        try {
            client.openConnection(login.getText(), password.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAuth(boolean success){
        login.clear();
        password.clear();
        authForm.setPrefHeight(0.0);
        authForm.setVisible(!success);
        chatForm.setVisible(success);
    }
}