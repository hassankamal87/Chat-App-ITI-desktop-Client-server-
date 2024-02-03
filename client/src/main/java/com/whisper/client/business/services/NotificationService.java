package com.whisper.client.business.services;

import com.whisper.client.HelloApplication;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.example.entities.Notification;
import org.example.entities.User;
import org.example.serverinterfaces.ContactServiceInt;
import org.example.serverinterfaces.NotificationServiceInt;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private Registry reg;
    private NotificationServiceInt notificationRef;

    public NotificationService() {
        try {
            reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            notificationRef = (NotificationServiceInt) reg.lookup("NotificationService");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Notification> getNotifications(int Id) {
        List<Notification> notifications = new ArrayList<>();
        try {


            notifications = notificationRef.getALLNotifications(Id);

        } catch (Exception e) {
            System.out.println("Exception is : " + e.getMessage());
        }
        return notifications;
    }

    public int deleteNotification(int Id) {
        int rowUpdate = 0;
        try {
            rowUpdate = notificationRef.deleteNotificationById(Id);

        } catch (Exception e) {
            System.out.println("Exception is : " + e.getMessage());
        }
        return rowUpdate;
    }

    public void sendInvitation(Notification notification) {
        Platform.runLater(()->{

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/notificationBodyView.fxml"));
            try {
                HBox hBox = fxmlLoader.load();

                Label l =(Label)hBox.getChildren().get(0);
                l.setText("You have a new invitation from "+notification.getFromUserName());
                Notifications.create()
                        .title("Invitation")
                        .graphic(hBox)
                        .hideAfter(Duration.seconds(10))
                        .show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }
    public void sendMessage(Notification notification) {
        Platform.runLater(()->{

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/notificationBodyView.fxml"));
            try {
                HBox hBox = fxmlLoader.load();

                Label l =(Label)hBox.getChildren().get(0);
                l.setText("You have a new message from "+notification.getFromUserName());
                Notifications.create()
                        .title("Message")
                        .graphic(hBox)
                        .hideAfter(Duration.seconds(10))
                        .show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }
}