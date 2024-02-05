package org.example.serverinterfaces;

import org.example.entities.Contact;
import org.example.entities.PendingRequest;
import org.example.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ContactServiceInt extends Remote {
    public List<User> getALLContacts(int Id) throws RemoteException;
    public List<User> getALLRequests(int Id) throws RemoteException;
    public int deletePendingRequest(int to_id,int from_id) throws RemoteException;
    public int addContact(Contact contact) throws RemoteException;
}
