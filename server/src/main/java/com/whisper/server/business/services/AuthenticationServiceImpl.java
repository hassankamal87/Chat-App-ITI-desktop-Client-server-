package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.db.MyDatabase;

import org.example.entities.User;
import org.example.serverinterfaces.AuthenticationServiceInt;
import org.mindrot.jbcrypt.BCrypt;

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
        try{
            UserDao.getInstance(MyDatabase.getInstance()).createUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public User loginUser(String phoneNumber, String password) throws RemoteException {
        User user= null;
        try {
            if (UserDao.getInstance(MyDatabase.getInstance()).isPhoneNumberExists(phoneNumber)){
                String hashedPassword = UserDao.getInstance(MyDatabase.getInstance()).getPasswordByPhoneNumber(phoneNumber);
                boolean validPassword = BCrypt.checkpw(password, hashedPassword);
                System.out.println(validPassword);
                if (validPassword) {
                    if (UserDao.getInstance(MyDatabase.getInstance()).getByPhoneAndPassword(phoneNumber, hashedPassword) != null) {
                        System.out.println("User signed in successfully");
                        return UserDao.getInstance(MyDatabase.getInstance()).getByPhoneAndPassword(phoneNumber, hashedPassword);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Sign in failed");
        return null;
    }

    @Override
    public boolean validatePhoneNumber(String phoneNo) throws RemoteException {
        try {
            if (UserDao.getInstance(MyDatabase.getInstance()).isPhoneNumberExists(phoneNo))
                return false;
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validateEmail(String email) throws RemoteException {
        try {
            if (UserDao.getInstance(MyDatabase.getInstance()).isEmailExists(email))
                return false;
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String hashPassword(String password) throws RemoteException {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }
}
