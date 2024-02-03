package org.example.serverinterfaces;

import org.example.entities.Contact;
import org.example.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ContactServiceInt extends Remote {
    public List<User> getALLContacts(int Id) throws RemoteException;
}
