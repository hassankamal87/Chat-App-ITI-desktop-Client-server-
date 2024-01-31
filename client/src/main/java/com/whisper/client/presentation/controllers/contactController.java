package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
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
    @FXML
    private Button addContactBtn;
    private BorderPane rootPane;
    @FXML
    private VBox contactsLayout;

    @FXML
    private ScrollPane scroll ;

    @FXML
    private AnchorPane mainContactPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        addContactBtn.setOnAction(event -> addContactAction());
        List<User> contacts = new ArrayList<>(contacts());
        for(int i=0;i<contacts.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/contactItemView.fxml"));

            try{
                HBox hBox=fxmlLoader.load();

                contactItemController cic = fxmlLoader.getController();
                cic.setData(contacts.get(i));
                hBox.setOnMouseClicked(event -> startChat());
                contactsLayout.getChildren().add(hBox);

            }catch (IOException e){
                e.printStackTrace();
            }



        }


    }

    private void addContactAction() {
        Parent root = SceneManager.getInstance().loadPane("userSearchView");
        Scene scene = mainContactPane.getScene();
        scene.setRoot(root);
    }

    private void startChat() {

        Parent root = SceneManager.getInstance().loadPane("notificationView");
        Scene scene = mainContactPane.getScene();
        scene.setRoot(root);


    }

    private List<User> contacts(){

        ContactService contactService= new ContactService();
        List<User>contacts =contactService.getContacts(1);
        return contacts;


    }


}