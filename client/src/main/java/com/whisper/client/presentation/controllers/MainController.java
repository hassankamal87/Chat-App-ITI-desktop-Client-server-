package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.business.services.ClientServiceImpl;
import com.whisper.client.presentation.services.SceneManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.example.clientinterfaces.ClientServiceInt;
import org.example.entities.User;
import org.example.serverinterfaces.SendContactsInvitationServiceInt;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Objects;

public class MainController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private AnchorPane navigationPane;
    @FXML
    private Button homeBtn;
    @FXML
    private Button contactsBtn;
    @FXML
    private Button profileBtn;
    @FXML
    private Button signOutBtn;
    @FXML
    private Button addContactBtn;

    private HashMap<String, Parent> panes = new HashMap<>();

    @FXML
    public void initialize() {
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/homeView.fxml"));
            root = fxmlLoader.load();
            panes.put("homePane", root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPane.setCenter(root);
        homeBtn.setStyle("-fx-background-color: #fe3554;");

    }

    @FXML
    public void onHomeClicked(Event event) {
        Parent root = null;

        if (!Objects.equals("homePane", mainPane.getCenter().getId())) {
            root = panes.get("homePane");

            mainPane.setCenter(root);
            homeBtn.setStyle("-fx-background-color: #fe3554;");
            addContactBtn.setStyle("-fx-background-color: #trnasparent;");
            contactsBtn.setStyle("-fx-background-color: #trnasparent;");
            profileBtn.setStyle("-fx-background-color: #trnasparent;");
        }

    }

    @FXML
    public void onContactsClicked(Event event) {
        if (!Objects.equals("mainContactPane", mainPane.getCenter().getId())) {
            Parent root = panes.get("mainContactPane");
            if (root == null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/contactView.fxml"));
                    root = fxmlLoader.load();

                    contactController controller = fxmlLoader.getController();
                    controller.setData(this);
                    panes.put("mainContactPane", root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            mainPane.setCenter(root);
            contactsBtn.setStyle("-fx-background-color: #fe3554;");
            homeBtn.setStyle("-fx-background-color: #trnasparent;");
            addContactBtn.setStyle("-fx-background-color: #trnasparent;");
            profileBtn.setStyle("-fx-background-color: #trnasparent;");
        }
    }

    @FXML
    public void onProfileClicked(Event event) {
        mainPane.setCenter(SceneManager.getInstance().loadPane("profileView"));
    }

    @FXML
    public void onSignOutClicked(Event event) throws RemoteException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry(1099);
        SendContactsInvitationServiceInt serverRef = (SendContactsInvitationServiceInt) reg.lookup("SendContactsInvitationService");
        ClientServiceInt clientService = ClientServiceImpl.getInstance();
        serverRef.ServerUnRegister(clientService);
    }

    @FXML
    public void onAddContactClicked(MouseEvent mouseEvent) {

        System.out.println("clicked first");
        if (!Objects.equals("userSearchPane", mainPane.getCenter().getId())) {
            Parent root = panes.get("userSearchPane");
            if (root == null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/userSearchView.fxml"));
                    root = fxmlLoader.load();

                    panes.put("userSearchPane", root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            mainPane.setCenter(root);
            addContactBtn.setStyle("-fx-background-color: #fe3554;");
            homeBtn.setStyle("-fx-background-color: #trnasparent;");
            contactsBtn.setStyle("-fx-background-color: #trnasparent;");
            profileBtn.setStyle("-fx-background-color: #trnasparent;");
        }
    }
    void navigateToHomeScreen(){
        Parent root = panes.get("homePane");

        mainPane.setCenter(root);
        homeBtn.setStyle("-fx-background-color: #fe3554;");
        addContactBtn.setStyle("-fx-background-color: #trnasparent;");
        contactsBtn.setStyle("-fx-background-color: #trnasparent;");
        profileBtn.setStyle("-fx-background-color: #trnasparent;");
    }
}