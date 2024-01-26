package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.presentation.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

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
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/continuingSignUpView.fxml"));
            root = fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }

        mainSignUpPane.setCenter(root);
    }

    public void onAlreadyHaveAccountClicked(ActionEvent actionEvent) {
    }
}