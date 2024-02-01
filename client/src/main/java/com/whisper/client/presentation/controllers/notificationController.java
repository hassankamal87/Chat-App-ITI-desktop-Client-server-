package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.entities.NotifactionType;
import org.example.entities.Notification;

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
        List<Notification> ls = new ArrayList<>();

        Notification notification1 = new Notification(1,2,"reem", NotifactionType.msg,"hi, hello");
        Notification notification2 = new Notification(2,3,"menna", NotifactionType.inv,"");

        ls.add(notification1);

        ls.add(notification2);


        return ls;
    }
}