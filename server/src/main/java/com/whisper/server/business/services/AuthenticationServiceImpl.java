package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.db.MyDatabase;
import org.example.entities.User;
import org.example.serverinterfaces.AuthenticationServiceInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class AuthenticationServiceImpl extends UnicastRemoteObject implements AuthenticationServiceInt {
    protected AuthenticationServiceImpl() throws RemoteException {
    }

    @Override
    public String createNewUser(User user) throws RemoteException {
        try {
            UserDao.getInstance(MyDatabase.getInstance()).createUser(user);
        } catch (SQLException e) {
            return e.getMessage();
        }
        return "done";
    }
}
