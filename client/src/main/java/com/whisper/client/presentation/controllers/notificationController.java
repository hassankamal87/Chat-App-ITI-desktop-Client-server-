package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.model.notification;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class notificationController implements Initializable {

    @FXML
    private VBox notificationLayout;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        List<notification> notifications = new ArrayList<>(notifications());

        for(int i=0;i<notifications.size();i++)
        {
            //System.out.printf(notifications.get(i).getFromUserName());
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/notificationItemView.fxml"));

            try{


                HBox hBox=fxmlLoader.load();
                notificationItemController nic =  fxmlLoader.getController();

                nic.setData(notifications.get(i));
                notificationLayout.getChildren().add(hBox);

            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }



    private List<notification> notifications() {
        List<notification> ls = new ArrayList<>();

        notification notification1 = new notification();
        notification notification2 = new notification();
        notification1.setType("message");
        notification1.setFromUserName("Reem Osama");
        notification1.setIcorSrc("messageIcon");
        ls.add(notification1);



        notification2.setType("invitation");
        notification2.setFromUserName("Hassan Kamal");
        notification2.setIcorSrc("invitationIcon");
        ls.add(notification2);


        return ls;
    }
}