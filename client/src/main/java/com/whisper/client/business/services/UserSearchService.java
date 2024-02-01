package com.whisper.client.business.services;

import org.example.serverinterfaces.SendContactsInvitationServiceInt;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class UserSearchService {
    public void sendInvitation(int id, List<String> contacts) {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            SendContactsInvitationServiceInt sendContactsInvitationInt = (SendContactsInvitationServiceInt) reg.lookup("SendContactsInvitationService");
            sendContactsInvitationInt.inviteContacts(id,contacts);
        } catch (Exception e) {
            System.out.println("Exception is : "+e.getMessage());
        }
    }
}
