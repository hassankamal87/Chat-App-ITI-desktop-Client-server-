package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.db.MyDatabase;
import org.example.entities.User;
import org.example.serverinterfaces.EditProfileServiceInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class EditProfileServiceImpl extends UnicastRemoteObject implements EditProfileServiceInt {
    private static EditProfileServiceImpl instance ;
    public static synchronized EditProfileServiceImpl getInstance() throws RemoteException {
        if(instance == null)
            instance = new EditProfileServiceImpl();
        return instance;
    }
    protected EditProfileServiceImpl() throws RemoteException {
    }

    @Override
    public User getUserData(int id) throws RemoteException {
        try {
            return UserDao.getInstance(MyDatabase.getInstance()).getUserById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveProfileChanges(User user) throws RemoteException {
        try {
            UserDao.getInstance(MyDatabase.getInstance()).updateUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
