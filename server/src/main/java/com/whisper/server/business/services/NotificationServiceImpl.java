package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.NotificationDao;

import com.whisper.server.persistence.db.MyDatabase;

import org.example.entities.Notification;

import org.example.serverinterfaces.NotificationServiceInt;

import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationServiceImpl extends UnicastRemoteObject implements NotificationServiceInt {
    private NotificationServiceImpl() throws RemoteException {
        super();
    }
    private static NotificationServiceImpl instance = null;


    public static synchronized NotificationServiceImpl getInstance() throws RemoteException {
        if(instance == null)
            instance = new NotificationServiceImpl();
        return instance;
    }

    @Override
    public List<Notification> getALLNotifications(int Id) throws RemoteException {
        List<Notification> resultNotification = new ArrayList<>();
        try {
            resultNotification= NotificationDao.getInstance(MyDatabase.getInstance()).getById(Id);

        }catch (SQLException e){
            System.out.println("SQL Exception : "+e);
            return null;
        }
        return resultNotification;
    }

    @Override
    public int deleteNotificationById(int Id) throws RemoteException {
        int rowupdates = 0;
        try {
            rowupdates= NotificationDao.getInstance(MyDatabase.getInstance()).deleteById(Id);

        }catch (SQLException e){
            System.out.println("SQL Exception : "+e);
        }
        return rowupdates;
    }
    public int addNotification(Notification notification){
        int rowupdates =0 ;
        try{
            rowupdates= NotificationDao.getInstance(MyDatabase.getInstance()).create(notification);
        }catch (SQLException e){
            System.out.println("SQL Exception : "+e);
        }
        return rowupdates;
    }
}
