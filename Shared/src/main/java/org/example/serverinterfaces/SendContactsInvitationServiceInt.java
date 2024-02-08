package org.example.serverinterfaces;

import org.example.clientinterfaces.ClientServiceInt;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SendContactsInvitationServiceInt extends Remote {
    public String inviteContacts(int id, String invitedContact) throws RemoteException;
    public void removeInvitation(int userId, int contactId) throws RemoteException;
    public void ServerRegister(ClientServiceInt clientService) throws RemoteException;

    public void ServerUnRegister(ClientServiceInt clientService) throws RemoteException;


    public void ServerUnRegisterWithId(int id) throws RemoteException;
}
