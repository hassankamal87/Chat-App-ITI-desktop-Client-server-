package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.MyApp;
import com.whisper.client.business.services.ChattingService;
import com.whisper.client.business.services.ContactService;

import com.whisper.client.presentation.services.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.entities.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class contactController implements Initializable
{

    private BorderPane rootPane;
    @FXML
    private VBox contactsLayout;

    @FXML
    private ScrollPane scroll ;

    @FXML
    private AnchorPane mainContactPane;

    private MainController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        List<User> contacts = new ArrayList<>(contacts());
        for(int i=0;i<contacts.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/contactItemView.fxml"));

            try{
                HBox hBox=fxmlLoader.load();
                contactItemController cic = fxmlLoader.getController();
                cic.setData(contacts.get(i));
                int finalI = i;
                hBox.setOnMouseClicked(event -> startChat(contacts.get(finalI)));
                contactsLayout.getChildren().add(hBox);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }



    public void setData(MainController mainController){
        this.mainController = mainController;
    }
    private void startChat(User contact) {
        System.out.println(contact.getUserId());
        ChattingService.getInstance().getOrCreateRoomChat(MyApp.getInstance().getCurrentUser().getUserId(),contact.getUserId());
        mainController.navigateToHomeScreen();
    }

    private List<User> contacts(){
        ContactService contactService= new ContactService();
        List<User>contacts =contactService.getContacts(MyApp.getInstance().getCurrentUser().getUserId());
        return contacts;
    }
}