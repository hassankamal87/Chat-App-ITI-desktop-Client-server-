package org.example.serverinterfaces;

import org.example.entities.RoomChat;
import org.example.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EditGroupServiceInt extends Remote {
    void saveProfileChanges(RoomChat roomChat) throws RemoteException;

}
