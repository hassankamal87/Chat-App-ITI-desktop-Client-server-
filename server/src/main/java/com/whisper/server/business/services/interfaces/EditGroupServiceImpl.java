package com.whisper.server.business.services.interfaces;

import com.whisper.server.business.services.EditProfileServiceImpl;
import com.whisper.server.persistence.daos.RoomChatDao;
import com.whisper.server.persistence.db.MyDatabase;
import org.example.entities.RoomChat;
import org.example.serverinterfaces.EditGroupServiceInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class EditGroupServiceImpl extends UnicastRemoteObject implements EditGroupServiceInt {
    private static EditGroupServiceImpl instance ;
    public static synchronized EditGroupServiceImpl getInstance() throws RemoteException {
        if(instance == null)
            instance = new EditGroupServiceImpl();
        return instance;
    }
    protected EditGroupServiceImpl() throws RemoteException {
    }

    @Override
    public void saveProfileChanges(RoomChat roomChat) throws RemoteException {
        try{
            RoomChatDao.getInstance(MyDatabase.getInstance()).updateRoomChat(roomChat);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
