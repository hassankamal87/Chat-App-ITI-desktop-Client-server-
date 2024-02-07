package com.whisper.client.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.example.entities.User;

public class ContactInGroupCreateController {
    @javafx.fxml.FXML
    private ImageView contactPhoto;
    @javafx.fxml.FXML
    private Text contactName;
    @javafx.fxml.FXML
    private Text contactNumber;
    @javafx.fxml.FXML
    private Circle modeColor;
    @javafx.fxml.FXML
    private Text modeStatus;
    @javafx.fxml.FXML
    private ToggleButton addedbutton;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void onAddClicked(ActionEvent actionEvent) {
    }

    public void setContact(User contact) {
    }
}