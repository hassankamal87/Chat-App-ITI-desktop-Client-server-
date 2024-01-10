package com.whisper.client.signup.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class ContinuingSignUpController
{
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private ComboBox country;
    @FXML
    private ImageView profilePicture;
    @FXML
    private RadioButton maleCheckBox;
    @FXML
    private RadioButton femaleCheckBox;
    @FXML
    private TextArea userBio;

    @FXML
    public void initialize() {
    }
    @FXML
    public void onProfilePicMouseClicked(Event event) {
        System.out.println("a7a");
    }
    @FXML
    public void onSignUpClicked(ActionEvent actionEvent) {

    }
}