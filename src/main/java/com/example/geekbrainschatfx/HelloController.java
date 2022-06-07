package com.example.geekbrainschatfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HelloController {
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

    private void sendMessage(){
        String message = messageInput.getText();
        messageInput.clear();
        conversationArea.appendText(String.format("%s\n", message));
    }
}