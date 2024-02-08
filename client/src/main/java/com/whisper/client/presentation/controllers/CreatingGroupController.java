package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.MyApp;
import com.whisper.client.business.services.ContactService;
import com.whisper.client.presentation.services.DialogueManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.entities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreatingGroupController
{
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox usersPane;

    ContactService contactService = new ContactService();

    List<User> contacts = new ArrayList<>();

    @FXML
    public void initialize() {
        for(User contact : contacts){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/contactItemView.fxml"));

            try {
                HBox contactItem = fxmlLoader.load();
                ContactInGroupCreateController contactController = fxmlLoader.getController();
                contactController.setContact(contact);


            } catch (IOException e) {
                DialogueManager.getInstance().showErrorDialog("Error", "Error in loading your contacts", "Please try again");
            }
        }
    }

    private void getAllContacts(){
        contacts = contactService.getContacts(MyApp.getInstance().getCurrentUser().getUserId());
    }

    public void onBackClicked(MouseEvent mouseEvent) {
    }

    public void onStartTheGroupClicked(ActionEvent actionEvent) {

    }
}