package com.whisper.client.presentation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.entities.NotifactionType;
import org.example.entities.Notification;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class notificationItemController implements Initializable {
    @FXML
    private Button deleteButton;

    @FXML
    private Label description;

    @FXML
    private Label header;

    @FXML
    private ImageView notificationIcon;


    public void setData(Notification notification){
        try{

            if(notification.getType()== NotifactionType.msg)
                notificationIcon.setImage( new Image(getClass().getResourceAsStream("/com/whisper/client/images/message.jpg")));
            else
                notificationIcon.setImage( new Image(getClass().getResourceAsStream("/com/whisper/client/images/invitation.jpg")));


            if(notification.getType()==NotifactionType.msg){
               header.setText("You hava a new message from "+ notification.getFromUserName());
               description.setText("you have a new message in  conversations from "+ notification.getFromUserName());
           }
           else if(notification.getType()==NotifactionType.inv){
               header.setText("You hava a new invitation from "+notification.getFromUserName());
               description.setText("you have a new friend request from "+ notification.getFromUserName());
           }
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
