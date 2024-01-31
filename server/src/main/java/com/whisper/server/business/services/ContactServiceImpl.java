package com.whisper.server.business.services;

import org.example.serverinterfaces.ContactServiceInt;
import com.whisper.server.persistence.daos.ContactDao;
import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.db.MyDatabase;
import org.example.entities.Contact;
import org.example.entities.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactServiceImpl extends UnicastRemoteObject implements ContactServiceInt {

    private static ContactServiceImpl instance = null;


    private ContactServiceImpl() throws RemoteException {
        super();
    }

    public static synchronized ContactServiceImpl getInstance() throws RemoteException {
        if(instance == null)
            instance = new ContactServiceImpl();
        return instance;
    }


    @Override
    public List<User> getALLContacts(int Id) throws RemoteException {
        System.out.println("hello");
        List<User> resultContact = new ArrayList<>();
        try {
            List<Contact>contacts = ContactDao.getInstance(MyDatabase.getInstance()).getById(Id);
            for(Contact c : contacts){
                int contactId=c.getContactId();
               User user = UserDao.getInstance(MyDatabase.getInstance()).getUserById(contactId);

               resultContact.add(user);
            }
        }catch (SQLException e){
            System.out.println("SQL Exception : "+e);
            return null;
        }
        return resultContact;
    }
}
