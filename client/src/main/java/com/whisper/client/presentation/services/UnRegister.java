package com.whisper.client.presentation.services;

import com.whisper.client.MyApp;
import com.whisper.client.business.services.ChattingService;
import com.whisper.client.business.services.ClientService;
import com.whisper.client.business.services.ClientServiceImpl;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.clientinterfaces.ClientServiceInt;
import org.example.serverinterfaces.SendContactsInvitationServiceInt;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class UnRegister {
    private static final UnRegister instance = new UnRegister();

    public static UnRegister getInstance() {
        return instance;
    }

    private UnRegister() {
    }


    public void unregister(){

        try {
            Registry reg = LocateRegistry.getRegistry(8000);
            SendContactsInvitationServiceInt serverRef = (SendContactsInvitationServiceInt) reg.lookup("SendContactsInvitationService");



            if(MyApp.getInstance().getCurrentUser()!=null){
                ClientServiceInt clientService = ClientServiceImpl.getInstance();
                serverRef.ServerUnRegister(clientService);

                //un register chat
                ClientService.getInstance().unRegisterChats();

                //un register user
                ChattingService.getInstance().unRegisterUser();
            }

        } catch (Exception e) {

            System.out.println("Exception is  : "+e.getMessage());
            Platform.exit();
            System.exit(0);

        }
    }


}
