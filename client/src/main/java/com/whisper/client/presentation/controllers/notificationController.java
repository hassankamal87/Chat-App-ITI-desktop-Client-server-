package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.business.services.ContactService;
import com.whisper.client.business.services.NotificationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    private ListView notificationLayout;

    @FXML
    private ScrollPane scrollPane;

    NotificationService notificationService= new NotificationService();
    HBox hBox;
    notificationItemController nic;
    List<Notification> notifications = new ArrayList<>(notifications());

    ObservableList<HBox> boxes = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        for(int i=0;i<notifications.size();i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/notificationItemView.fxml"));
            try {
                hBox = fxmlLoader.load();
                nic = fxmlLoader.getController();
                nic.setData(notifications.get(i));
                boxes.add(hBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        notificationLayout.setItems(boxes);
        for (int i=0;i<boxes.size();i++){
            Button b = (Button) boxes.get(i).getChildren().get(2);
            int finalI = i;
            b.setOnAction(event->deleteAction(event, finalI));
        }
    }
    private void deleteAction(Event event,int i) {
        Button deleteButton = (Button) event.getSource();
        HBox notificationBox = (HBox) deleteButton.getParent();
        boxes.remove(notificationBox);
        Notification not=notifications.get(i);
        notifications.remove(not);
        notificationService.deleteNotification(not.getNotificationId());
        notificationService.sendMessage(not);
    }
    private List<Notification> notifications() {
        List<Notification>notifications =notificationService.getNotifications(7);
        return notifications;
    }
}