package com.whisper.client.signup.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SignUpController {
    @javafx.fxml.FXML
    private TextField firstName;
    @javafx.fxml.FXML
    private TextField lastName;
    @javafx.fxml.FXML
    private TextField email;
    @javafx.fxml.FXML
    private TextField phoneNumber;
    @FXML
    private BorderPane mainSignUpPane;
    @FXML
    private Pane firstSignUpSubPane;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;


    public void onGetStartedClicked(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = javafx.fxml.FXMLLoader.load(getClass().getResource("../view/continuingSignUpView.fxml"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        mainSignUpPane.setCenter(root);
    }

    public void onAlreadyHaveAccountClicked(ActionEvent actionEvent) {
    }
}