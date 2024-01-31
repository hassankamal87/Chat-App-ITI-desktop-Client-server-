package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.business.services.ContactService;
import com.whisper.client.model.contact;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
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
    @javafx.fxml.FXML
    private VBox contactsLayout;

    @javafx.fxml.FXML
    private ScrollPane scroll ;


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
                contactsLayout.getChildren().add(hBox);

            }catch (IOException e){
                e.printStackTrace();
            }


        }

    }

    private List<User> contacts(){

        ContactService contactService= new ContactService();
        List<User>contacts =contactService.getContacts(1);
        return contacts;


    }


}