package com.whisper.client.presentation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.entities.NotifactionType;
import org.example.entities.Notification;
import org.example.entities.PendingRequest;
import org.example.entities.User;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class requestItemController implements Initializable {
    @FXML
    private Button deleteButton;

    @FXML
    private Label description;

    @FXML
    private Label header;

    @FXML
    private ImageView notificationIcon;



    @FXML
    private Button acceptButton;
    public void setData(User user){
        try{


                notificationIcon.setImage( new Image(new ByteArrayInputStream(user.getProfilePhoto())));
                header.setText("You hava a new invitation from "+user.getUserName());



        }catch (Exception e){
            e.printStackTrace();
        }

        try{

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }



}
