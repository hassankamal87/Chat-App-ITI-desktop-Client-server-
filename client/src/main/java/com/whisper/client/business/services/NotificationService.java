package com.whisper.client.business.services;

import org.example.entities.Notification;
import org.example.entities.User;
import org.example.serverinterfaces.ContactServiceInt;
import org.example.serverinterfaces.NotificationServiceInt;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private Registry reg ;
    private NotificationServiceInt notificationRef ;

    public NotificationService() {
        try {
            reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            notificationRef= (NotificationServiceInt) reg.lookup("NotificationService");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Notification> getNotifications(int Id) {
        List<Notification>notifications =new ArrayList<>();
        try {


            notifications=notificationRef.getALLNotifications(Id);

        }catch (Exception e){
            System.out.println("Exception is : "+e.getMessage());
        }
        return notifications;
    }
    public int deleteNotification(int Id) {
        int rowUpdate = 0;
        try {
         rowUpdate=notificationRef.deleteNotificationById(Id);

        }catch (Exception e){
            System.out.println("Exception is : "+e.getMessage());
        }
        return rowUpdate;
    }
}
