package com.example.geekbrainschatfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class HelloController {
    @FXML
    private TextArea mainTextArea;

    @FXML
    public void btnOneClickAction(ActionEvent actionEvent) {
        mainTextArea.appendText("1\n");
    }
}