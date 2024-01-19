package com.whisper.client.notification.controller;

import com.whisper.client.model.notification;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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


    public void setData(notification notification){
        try{

            notificationIcon.setImage( new Image(getClass().getResourceAsStream("/com/whisper/client/images/notificationIcon/"+notification.getIcorSrc()+".png")));

            if(Objects.equals(notification.getType(), "message")){
               header.setText("You hava a new message from Hassan");
               description.setText("you have a new message in  conversations from Hassan Kamal");
           }
           else if(Objects.equals(notification.getType(), "invitation")){
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
