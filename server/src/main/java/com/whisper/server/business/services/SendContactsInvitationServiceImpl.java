package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.ContactDao;
import com.whisper.server.persistence.daos.NotificationDao;
import com.whisper.server.persistence.daos.PendingRequestDao;
import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.db.MyDatabase;
import javafx.application.Platform;
import org.example.clientinterfaces.ClientServiceInt;
import org.example.entities.*;
import org.example.serverinterfaces.NotificationServiceInt;
import org.example.serverinterfaces.SendContactsInvitationServiceInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SendContactsInvitationServiceImpl extends UnicastRemoteObject implements SendContactsInvitationServiceInt {

    public static CopyOnWriteArrayList<ClientServiceInt> clientsVector = new CopyOnWriteArrayList<>();

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
                ///wait
                if (contactID == -1){
                    System.out.println("not found");
                    continue;
                }
                else if (contactID==id){
                    System.out.println("same phone");
                    continue;
                }

                    // add invitation to pending requests
                PendingRequest request = new PendingRequest(contactID, id, Date.valueOf(LocalDate.now()).toString(), "I want to add you");
                PendingRequestDao.getInstance(MyDatabase.getInstance()).createPendingRequest(request);



              
                User user =UserDao.getInstance(MyDatabase.getInstance()).getUserById(id);
                String userName = user.getUserName();
                // send notification
                sendNotification(contactID,userName);
                for(ClientServiceInt c:clientsVector){
                    if(c.getClientId()==contactID){
                        c.receiveNotification(new Notification(1,id,user.getUserName(),NotifactionType.inv,"invitation"));
                    }
                }

                System.out.println("Invitation sent");
            } catch (Exception e) {
                System.out.println("SQL Exception : " + e);
            }
        }
    }

    private void sendNotification(int contactID,String userName) throws RemoteException {
        NotificationServiceImpl notificationService =  NotificationServiceImpl.getInstance();
        notificationService.addNotification( new Notification(0, contactID, userName,
                NotifactionType.inv, "I want to add you"));


    }

    @Override
    public void removeInvitation(int contactId, int userId) throws RemoteException {
        try {
            PendingRequestDao.getInstance(MyDatabase.getInstance()).deletePendingRequest(userId, contactId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void ServerRegister(ClientServiceInt clientService) throws RemoteException {

        clientsVector.add(clientService);
        try{

           User user = UserDao.getInstance(MyDatabase.getInstance()).getUserById(clientService.getClientId());
            user.setStatus(Status.online);
            User user1=new User(clientService.getClientId(), user.getPhoneNumber(), user.getPassword(), user.getEmail(), user.getUserName(), user.getGender(),user.getDateOfBirth(),user.getCountry(), user.getBio(), user.getMode(),Status.online, user.getProfilePhoto());
            UserDao.getInstance(MyDatabase.getInstance()).updateUser(user1);
           ContactDao contactRef = (ContactDao) ContactDao.getInstance(MyDatabase.getInstance());
            for(ClientServiceInt c: clientsVector){
                if(c!=clientService&&contactRef.isContact(clientService.getClientId(),c.getClientId())){
                    c.ClientStatusAnnounce(user1);
                }
            }

            Platform.runLater(()->{
                ServerStatistics.getInstance().updateData();
            });

        }catch (SQLException e){
            System.out.println("SQL Exception is :" + e.getMessage());
        }
    }

    @Override
    public void ServerUnRegister(ClientServiceInt clientService) throws RemoteException {

        System.out.println(clientService.getClientId());
        try{
            User user = UserDao.getInstance(MyDatabase.getInstance()).getUserById(clientService.getClientId());

            clientsVector.remove(clientService);
            User user1=new User(clientService.getClientId(), user.getPhoneNumber(), user.getPassword(), user.getEmail(), user.getUserName(), user.getGender(),user.getDateOfBirth(),user.getCountry(), user.getBio(), user.getMode(),Status.offline, user.getProfilePhoto());
            UserDao.getInstance(MyDatabase.getInstance()).updateUser(user1);
            ContactDao contactRef = (ContactDao) ContactDao.getInstance(MyDatabase.getInstance());

            for(ClientServiceInt c: clientsVector){
                if(c!=clientService&&contactRef.isContact(clientService.getClientId(),c.getClientId())){
                    System.out.println("notify "+c.getClientId());
                    c.ClientStatusAnnounce(user1);
                }
            }

            Platform.runLater(()->{
                ServerStatistics.getInstance().updateData();
            });
        }catch (SQLException e){
            System.out.println("SQL Exception is :" + e.getMessage());
        }

    }

    private boolean alreadyGotInvite(int userId,int contactId) {
        PendingRequest result = null;
        try {
            result = PendingRequestDao.getInstance(MyDatabase.getInstance()).getPendingRequest(userId, contactId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result != null;
    }
}

