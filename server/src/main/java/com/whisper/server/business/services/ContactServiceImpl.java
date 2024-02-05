package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.NotificationDao;
import com.whisper.server.persistence.daos.PendingRequestDao;
import org.example.entities.PendingRequest;
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

    @Override
    public List<User> getALLRequests(int Id) throws RemoteException {
        List<User> result = new ArrayList<>();
        try {
            List<PendingRequest>requests = PendingRequestDao.getInstance(MyDatabase.getInstance()).getPendingRequest(Id);
            for(PendingRequest c : requests){
                int contactId=c.getFromUserId();
                User user = UserDao.getInstance(MyDatabase.getInstance()).getUserById(contactId);

                result.add(user);
            }
        }catch (SQLException e){
            System.out.println("SQL Exception : "+e);
            return null;
        }
        return result;
    }

    @Override
    public int deletePendingRequest(int to_id, int from_id) throws RemoteException {
        int rowupdates = 0;
        try {
            rowupdates= PendingRequestDao.getInstance(MyDatabase.getInstance()).deletePendingRequest(to_id,from_id);

        }catch (SQLException e){
            System.out.println("SQL Exception : "+e);
        }
        return rowupdates;
    }

    @Override
    public int addContact(Contact contact) throws RemoteException {
        int rowupdates =0 ;
        try{
            rowupdates= ContactDao.getInstance(MyDatabase.getInstance()).create(contact);
        }catch (SQLException e){
            System.out.println("SQL Exception : "+e);
        }
        return rowupdates;

    }
}
