package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.business.services.ContactService;
import com.whisper.client.business.services.NotificationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.entities.NotifactionType;
import org.example.entities.Notification;
import org.example.entities.User;

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



        List<Notification> notifications = new ArrayList<>(notifications());

        for(int i=0;i<notifications.size();i++)
        {
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



    private List<Notification> notifications() {

        NotificationService notificationService= new NotificationService();
        List<Notification>notifications =notificationService.getNotifications(1);

        return notifications;
    }
}