package com.whisper.server.business.services;

import com.whisper.server.business.services.interfaces.serverServiceInt;
import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.daos.interfaces.UserDaoInterface;
import com.whisper.server.persistence.db.MyDatabase;
import org.example.entities.User;
import org.example.serverinterfaces.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerService implements serverServiceInt {

    private static ServerService instance = null;
    private ServerService(){}
    public static synchronized ServerService getInstance(){
        if(instance == null)
            instance = new ServerService();
        return instance;
    }
    private Registry reg;
    private MyDatabase myDatabase = MyDatabase.getInstance();
    private UserDaoInterface userDao = UserDao.getInstance(myDatabase);

    @Override
    public void startServer() {
        try{
            openRmiConnection();
            myDatabase.startConnection();
            System.out.println("Connect now to database");
        }catch(SQLException e){
            System.out.println("failed connection to database ,SQLException is : "+e.getMessage());
        }
    }

    private void openRmiConnection() {
        try {
            reg = LocateRegistry.createRegistry(1099);
            AuthenticationServiceInt authenticationService = new AuthenticationServiceImpl();
            ContactServiceInt contactService =  ContactServiceImpl.getInstance();
            SendContactsInvitationServiceInt sendContactsInvitationService = SendContactsInvitationServiceImpl.getInstance();
            AddContactsServiceInt addContactsService = AddContactsServiceImpl.getInstance();
            ChatServiceInt chatService = ChatServiceImpl.getInstance();
            reg.rebind("AddContactsService", addContactsService);
            reg.rebind("SendContactsInvitationService", sendContactsInvitationService);
            reg.rebind("authenticationService", authenticationService);
            reg.rebind("ContactsService",contactService);
            System.out.println("AddContactsService binded successful");
            System.out.println("SendContactsInvitationService binded successful");
            NotificationServiceInt notificationService = NotificationServiceImpl.getInstance();
            reg.rebind("authenticationService", authenticationService);
            reg.rebind("ContactsService",contactService);
            reg.rebind("NotificationService",notificationService);
            reg.rebind("ChatService",chatService);
            System.out.println("authenticationService binded successful");
            System.out.println("ContactService binded successful");
            System.out.println("NotificationService binded successful");

        } catch (RemoteException e) {
            System.out.println(e.getMessage() + " Server Service line 36");
        }
    }

    private void closeRmiConnection() {
            try {
                // Unexport the RMI registry to close the connection
                UnicastRemoteObject.unexportObject(reg, true);
                System.out.println("RMI registry closed successfully.");
            } catch (RemoteException e) {
                e.printStackTrace();
            }

    }
    @Override
    public void stopServer() {
        try {
            closeRmiConnection();
            myDatabase.closeConnection();
            System.out.println("Connection closed on application shutdown.");
        } catch (SQLException e) {
            System.out.println("failed to close connection to database ,SQLException is : "+e.getMessage());
        }
    }

    @Override
    public void Announcement(String s) {
        System.out.println("hello announcment");
    }

    @Override
    public List<User> viewClients() {
        List<User>clients =new ArrayList<>();
        try {
            clients=userDao.getAll();
        } catch (SQLException e) {
            System.out.println("failed to view Clients from database ,SQLException is : " + e.getMessage());
        }
       return clients;
    }
}
