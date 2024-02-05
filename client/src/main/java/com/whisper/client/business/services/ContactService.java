package com.whisper.client.business.services;

import org.example.entities.Contact;
import org.example.entities.FriendshipStatus;
import org.example.entities.PendingRequest;
import org.example.entities.User;
import org.example.serverinterfaces.ContactServiceInt;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContactService {

    public List<User> getContacts(int Id) {
        List<User>contacts =new ArrayList<>();
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            ContactServiceInt contactRef = (ContactServiceInt) reg.lookup("ContactsService");
            contacts=contactRef.getALLContacts(Id);
        }catch (Exception e){
            System.out.println("Exception is : "+e.getMessage());
        }
        return contacts;
    }
    public List<User> getRequests(int Id) {
        List<User>requests =new ArrayList<>();
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            ContactServiceInt contactRef = (ContactServiceInt) reg.lookup("ContactsService");
            requests=contactRef.getALLRequests(Id);
        }catch (Exception e){
            System.out.println("Exception is : "+e.getMessage());
        }
        return requests;
    }
    public int deleleteRequest(int to_Id,int from_Id) {
        int rowUpdate = 0;
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            ContactServiceInt contactRef = (ContactServiceInt) reg.lookup("ContactsService");

            rowUpdate = contactRef.deletePendingRequest(to_Id,from_Id);

        } catch (Exception e) {
            System.out.println("Exception is : " + e.getMessage());
        }
        return rowUpdate;
    }

    public int addContact(int user_id,int contact_id){
        int rowUpdate = 0;
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            ContactServiceInt contactRef = (ContactServiceInt) reg.lookup("ContactsService");

            rowUpdate = contactRef.addContact(new Contact(FriendshipStatus.friend, Date.valueOf(LocalDate.now()),user_id,contact_id));

        } catch (Exception e) {
            System.out.println("Exception is : " + e.getMessage());
        }
        return rowUpdate;
    }
}
