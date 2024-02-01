package com.whisper.client.business.services;

import org.example.entities.Notification;
import org.example.entities.User;
import org.example.serverinterfaces.ContactServiceInt;
import org.example.serverinterfaces.NotificationServiceInt;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    public List<Notification> getNotifications(int Id) {
        List<Notification>notifications =new ArrayList<>();
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            NotificationServiceInt notificationRef = (NotificationServiceInt) reg.lookup("NotificationService");
            notifications=notificationRef.getALLNotifications(Id);
            System.out.println(notificationRef.getALLNotifications(Id).size());
        }catch (Exception e){
            System.out.println("Exception is : "+e.getMessage());
        }
        return notifications;
    }
}
