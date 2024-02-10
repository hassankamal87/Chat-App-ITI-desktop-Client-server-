package com.whisper.client.presentation.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.example.entities.User;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupMemberController implements Initializable {
    @javafx.fxml.FXML
    private HBox groupMember;
    @javafx.fxml.FXML
    private Label memberName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(User user){
        memberName.setText(user.getUserName());
    }
}
