package com.whisper.client.business.services;

import org.example.clientinterfaces.ClientInterface;
import org.example.entities.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientService extends UnicastRemoteObject implements ClientInterface {


    private static ClientService instance = null;

    public static synchronized ClientService getInstance() throws RemoteException {
        if (instance == null){
            instance = new ClientService();
        }
        return instance;
    }
    protected ClientService() throws RemoteException {

    }

    @Override
    public void notifyUserWithMessage(Message message) throws RemoteException {
        System.out.println(message.getBody());
    }
}
