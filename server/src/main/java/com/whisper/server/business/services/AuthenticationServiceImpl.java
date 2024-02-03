package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.db.MyDatabase;

import org.example.entities.User;
import org.example.serverinterfaces.AuthenticationServiceInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class AuthenticationServiceImpl extends UnicastRemoteObject implements AuthenticationServiceInt {
    private static AuthenticationServiceImpl instance=null;

    public static synchronized AuthenticationServiceImpl getInstance() throws RemoteException {
        if(instance==null){
            instance=new AuthenticationServiceImpl();
        }
        return instance;
    }
    private AuthenticationServiceImpl() throws RemoteException{
        // super();
    }

    @Override
    public boolean registerUser(User user) throws RemoteException {
//        UserValidation userValidation = new UserValidation(MyDatabase.getInstance());
        try{
//            if (userValidation.isEmailExists(user.getEmail()) ||
//                userValidation.isPhoneNumberExists(user.getPhoneNumber())){
//                System.out.println("Email or phoneNumber duplicated");
//                return false;
//            }
            UserDao.getInstance(MyDatabase.getInstance()).createUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
