package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.ContactDao;
import com.whisper.server.persistence.daos.NotificationDao;
import com.whisper.server.persistence.daos.PendingRequestDao;
import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.db.MyDatabase;
import org.example.entities.*;
import org.example.serverinterfaces.SendContactsInvitationServiceInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SendContactsInvitationServiceImpl extends UnicastRemoteObject implements SendContactsInvitationServiceInt {
    private static SendContactsInvitationServiceImpl instance = null;

    private SendContactsInvitationServiceImpl() throws RemoteException {
        // super();
    }

    public static synchronized SendContactsInvitationServiceImpl getInstance() throws RemoteException {
        if (instance == null)
            instance = new SendContactsInvitationServiceImpl();
        return instance;
    }

    @Override
    public void inviteContacts(int id, List<String> invitedContacts) throws RemoteException {
        for (String invitedContact : invitedContacts) {
            try {
                int contactID = UserDao.getInstance(MyDatabase.getInstance()).getIdByPhoneNumber(invitedContact);
                if (alreadyGotInvite(id, contactID)) {
                    ContactDao.getInstance(MyDatabase.getInstance())
                            .create(new Contact(FriendshipStatus.friend,
                                    Date.valueOf(LocalDate.now()), id, contactID));
                    removeInvitation(contactID, id);
                    continue;
                }
                if (contactID == -1||contactID==id) {
                    continue;
                }

                // add invitation to pending requests
                PendingRequest request = new PendingRequest(contactID, id, Date.valueOf(LocalDate.now()).toString(), "I want to add you");
                PendingRequestDao.getInstance(MyDatabase.getInstance()).createPendingRequest(request);

                // send notification
                String userName = UserDao.getInstance(MyDatabase.getInstance()).getUserById(id).getUserName();
                sendNotification(contactID,userName);
                System.out.println("Invitation sent");
            } catch (Exception e) {
                System.out.println("SQL Exception : " + e);
            }
        }
    }

    private void sendNotification(int contactID,String userName) {
        Notification notification = new Notification(0, contactID, userName,
                NotifactionType.inv, "I want to add you");
        try {
            System.out.println("notification updated: " + NotificationServiceImpl.getInstance().addNotification(notification));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeInvitation(int contactId, int userId) throws RemoteException {
        try {
            PendingRequestDao.getInstance(MyDatabase.getInstance()).deletePendingRequest(userId, contactId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean alreadyGotInvite(int userId, int contactId) {
        PendingRequest result = null;
        try {
            result = PendingRequestDao.getInstance(MyDatabase.getInstance()).getPendingRequest(userId, contactId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result != null;
    }
}

