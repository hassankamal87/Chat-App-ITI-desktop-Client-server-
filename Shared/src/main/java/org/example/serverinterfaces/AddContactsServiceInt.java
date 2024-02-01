package org.example.serverinterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface AddContactsServiceInt extends Remote{
    public void addContact(int userId, int contactId) throws RemoteException;
    public void deleteContact(int userId, int contactId) throws RemoteException;
    public void blockContact(int userId, int contactId) throws RemoteException;
    public void unblockContact(int userId, int contactId) throws RemoteException;
}
