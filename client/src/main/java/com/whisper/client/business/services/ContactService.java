package com.whisper.client.business.services;

import org.example.entities.User;
import org.example.serverinterfaces.ContactServiceInt;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
        //System.out.println("done"+contacts.size());
        return contacts;
    }
}
