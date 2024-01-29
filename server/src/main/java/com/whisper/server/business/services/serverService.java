package com.whisper.server.business.services;

import com.whisper.server.business.services.interfaces.serverServiceInt;
import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.daos.interfaces.UserDaoInterface;
import com.whisper.server.persistence.db.MyDatabase;
import org.example.entities.User;
import org.example.serverinterfaces.AuthenticationServiceInt;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerService implements serverServiceInt {
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
            Registry reg = LocateRegistry.createRegistry(1099);
            AuthenticationServiceInt authenticationService = new AuthenticationServiceImpl();
            reg.rebind("authenticationService", authenticationService);
            System.out.println("authenticationService binded successful");
        } catch (RemoteException e) {
            System.out.println(e.getMessage() + "Server Service line 36");
        }
    }

    @Override
    public void stopServer() {
        try {
            myDatabase.closeConnection();
            System.out.println("Connection closed on application shutdown.");
        } catch (SQLException e) {
            System.out.println("failed to close connection to database ,SQLException is : "+e.getMessage());
        }
    }

    @Override
    public void Announcement(String s) {

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
