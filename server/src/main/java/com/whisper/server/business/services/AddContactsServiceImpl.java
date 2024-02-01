package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.ContactDao;
import com.whisper.server.persistence.db.MyDatabase;
import org.example.entities.Contact;
import org.example.entities.FriendshipStatus;
import org.example.serverinterfaces.AddContactsServiceInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddContactsServiceImpl extends UnicastRemoteObject implements AddContactsServiceInt {
    private static AddContactsServiceImpl instance=null;


    public static synchronized AddContactsServiceImpl getInstance() throws RemoteException {
        if(instance==null){
            instance=new AddContactsServiceImpl();
        }
        return instance;
    }
    private AddContactsServiceImpl() throws RemoteException{
        // super();
    }

    @Override
    public void addContact(int userId, int contactId) throws RemoteException {
        Contact contact = new Contact(FriendshipStatus.friend, Date.valueOf(LocalDate.now()), userId, contactId);
        try {
            if(!ContactDao.getInstance(MyDatabase.getInstance()).isContact(userId, contactId)){
                ContactDao.getInstance(MyDatabase.getInstance()).create(contact);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteContact(int userId, int contactId) throws RemoteException {
        try {
            ContactDao.getInstance(MyDatabase.getInstance()).deleteById(userId, contactId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void blockContact(int userId, int contactId) throws RemoteException {
        try{
            ContactDao.getInstance(MyDatabase.getInstance()).blockContact(userId,contactId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unblockContact(int userId, int contactId) throws RemoteException {
        try{
            ContactDao.getInstance(MyDatabase.getInstance()).unblockContact(userId,contactId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
