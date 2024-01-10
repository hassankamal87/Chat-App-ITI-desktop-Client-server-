package com.whisper.client.showProfile.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class profileController {
    @javafx.fxml.FXML
    private ImageView userPicture;
    @javafx.fxml.FXML
    private TextField userName;
    @javafx.fxml.FXML
    private TextField userEmail;
    @javafx.fxml.FXML
    private TextArea userBio;
    @javafx.fxml.FXML
    private RadioButton maleGender;
    @javafx.fxml.FXML
    private RadioButton femaleGender;
    @javafx.fxml.FXML
    private ChoiceBox userMode;
    @javafx.fxml.FXML
    private ChoiceBox userCountry;
    @javafx.fxml.FXML
    private DatePicker userDob;
    @javafx.fxml.FXML
    private Button saveChanges;
    @javafx.fxml.FXML
    private ToggleGroup genderToggleGroup;

    @FXML
    public void initialize() {
        genderToggleGroup = new ToggleGroup();
        maleGender.setToggleGroup(genderToggleGroup);
        femaleGender.setToggleGroup(genderToggleGroup);
    }
}
