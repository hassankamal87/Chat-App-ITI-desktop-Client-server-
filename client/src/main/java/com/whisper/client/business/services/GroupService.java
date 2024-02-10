package com.whisper.client.business.services;

import org.example.entities.RoomChat;
import org.example.serverinterfaces.EditGroupServiceInt;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GroupService {
    private EditGroupServiceInt editService;
    private Registry reg;
    public void editGroup(RoomChat roomChat){
        try{
            reg = LocateRegistry.getRegistry("127.0.0.1", 8000);
            editService = (EditGroupServiceInt) reg.lookup("GroupService");
            editService.saveProfileChanges(roomChat);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
