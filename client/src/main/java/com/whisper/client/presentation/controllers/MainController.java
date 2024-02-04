package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.presentation.services.SceneManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private AnchorPane navigationPane;
    @FXML
    private Button homeBtn;
    @FXML
    private Button contactsBtn;
    @FXML
    private Button profileBtn;
    @FXML
    private Button signOutBtn;
    @FXML
    private Button addContactBtn;

    @FXML
    public void initialize() {
        Parent root = null;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/homeView.fxml"));
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPane.setCenter(root);
        homeBtn.setStyle("-fx-background-color: #fe3554;");
    }

    @FXML
    public void onHomeClicked(Event event) {
        Parent root = null;

        if (!Objects.equals("homePane", mainPane.getCenter().getId())) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/homeView.fxml"));


                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mainPane.setCenter(root);
            homeBtn.setStyle("-fx-background-color: #fe3554;");
            addContactBtn.setStyle("-fx-background-color: #trnasparent;");
            contactsBtn.setStyle("-fx-background-color: #trnasparent;");
            profileBtn.setStyle("-fx-background-color: #trnasparent;");
        }

    }

    @FXML
    public void onContactsClicked(Event event) {

    }

    @FXML
    public void onProfileClicked(Event event) {
        mainPane.setCenter(SceneManager.getInstance().loadPane("profileView"));
    }

    @Deprecated
    public void onMouseEnteredProfileBtn(Event event) {
    }

    @FXML
    public void onSignOutClicked(Event event) {
    }

    public void onAddContactClicked(MouseEvent mouseEvent) {
        Parent root = null;

        if (!Objects.equals("userSearchPane", mainPane.getCenter().getId())) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/userSearchView.fxml"));


                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mainPane.setCenter(root);
            addContactBtn.setStyle("-fx-background-color: #fe3554;");
            homeBtn.setStyle("-fx-background-color: #trnasparent;");
            contactsBtn.setStyle("-fx-background-color: #trnasparent;");
            profileBtn.setStyle("-fx-background-color: #trnasparent;");
        }
    }

    @FXML
    public void onAddContactClicked(Event event) {
    }
}