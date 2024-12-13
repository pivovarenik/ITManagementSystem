package org.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OutgoingMessageController {
    @FXML
    public Label content;
    @FXML
    public Label time;
    public void setContent(String content1,String content2) {
        this.content.setText(content1);
        this.time.setText(content2);
    }
}
