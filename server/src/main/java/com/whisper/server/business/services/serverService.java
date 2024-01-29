package com.whisper.server.business.services;

import com.whisper.server.business.services.interfaces.serverServiceInt;
import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.daos.interfaces.UserDaoInterface;
import com.whisper.server.persistence.db.MyDatabase;
import com.whisper.server.persistence.entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class serverService implements serverServiceInt {
    private MyDatabase myDatabase = MyDatabase.getInstance();
    private UserDaoInterface userDao = UserDao.getInstance(myDatabase);

    @Override
    public void startServer() {
        try{
            myDatabase.startConnection();
            System.out.println("Connect now to database");
        }catch(SQLException e){
            System.out.println("failed connection to database ,SQLException is : "+e.getMessage());
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
