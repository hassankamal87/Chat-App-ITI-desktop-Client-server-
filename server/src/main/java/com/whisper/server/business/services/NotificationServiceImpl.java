package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.ContactDao;
import com.whisper.server.persistence.daos.NotificationDao;
import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.db.MyDatabase;
import org.example.entities.Contact;
import org.example.entities.Notification;
import org.example.entities.User;
import org.example.serverinterfaces.ContactServiceInt;
import org.example.serverinterfaces.NotificationServiceInt;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationServiceImpl extends UnicastRemoteObject implements NotificationServiceInt {
    protected NotificationServiceImpl() throws RemoteException {
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
}
