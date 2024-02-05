package com.whisper.client.business.services;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.serverinterfaces.SendContactsInvitationServiceInt;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class UserSearchService {
    public void sendInvitation(int id, List<String> contacts) {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 8000);
            SendContactsInvitationServiceInt sendContactsInvitationInt = (SendContactsInvitationServiceInt) reg.lookup("SendContactsInvitationService");
            for(String contact :contacts){
                String inform ;
                String  response =sendContactsInvitationInt.inviteContacts(id,contact);
                if(response.equals("already got invited")){
                    inform="You already now friend with this Number : "+contact;
                    Alert alert = new Alert(Alert.AlertType.WARNING, inform, ButtonType.OK);
                    alert.showAndWait();
                }else if(response.equals("automatic Invitation")){
                    inform="Done ,Now you can send a message to "+contact;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, inform, ButtonType.OK);
                    alert.showAndWait();
                }
                else if(response.equals("Not Found")){
                    inform="This Phone Number "+contact+" is Not Found.";
                    Alert alert = new Alert(Alert.AlertType.ERROR, inform, ButtonType.OK);
                    alert.showAndWait();
                }
                else if(response.equals("self invitation")){
                    inform="You can not be friend with your self!.";
                    Alert alert = new Alert(Alert.AlertType.WARNING, inform, ButtonType.OK);
                    alert.showAndWait();
                }
                else if(response.equals("Invitation Send Successfully")){
                    inform="Invitation Send Successfully";
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, inform, ButtonType.OK);
                    alert.showAndWait();
                }

            }


        } catch (Exception e) {
            System.out.println("Exception is : "+e.getMessage());
            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }
}
