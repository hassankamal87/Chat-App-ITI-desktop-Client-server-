package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.db.MyDatabase;
import org.example.entities.User;

import java.sql.SQLException;
import java.util.List;

public class ClientsProfile {
    private ClientsProfile clientsProfile;
    private ClientsProfile(){}
    public synchronized ClientsProfile getInstance(){
        if(clientsProfile == null){
            clientsProfile = new ClientsProfile();
        }
        return clientsProfile;
    }
    public List<User> getAllUsers() throws SQLException {
        return UserDao.getInstance(MyDatabase.getInstance()).getAll();
    }
    public void updateUser(User user) throws SQLException {
        UserDao.getInstance(MyDatabase.getInstance()).updateUser(user);
    }
    public void deleteUser(User user) throws SQLException {
        UserDao.getInstance(MyDatabase.getInstance()).deleteById(user.getUserId());
    }
}
