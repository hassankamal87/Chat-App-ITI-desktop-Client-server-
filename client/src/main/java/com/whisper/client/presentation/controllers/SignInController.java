package com.whisper.client.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @javafx.fxml.FXML
    private TextField phoneNumber;
    @javafx.fxml.FXML
    private PasswordField password;
    @javafx.fxml.FXML
    private CheckBox rememberMeChecked;

    @javafx.fxml.FXML
    public void initialize() {
    }

    public void onSigninButtonClick(ActionEvent actionEvent) {
    }

    public void onSignupButtonClick(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}