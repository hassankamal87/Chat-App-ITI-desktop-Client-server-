package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.business.services.ChattingService;
import com.whisper.client.business.services.ClientService;
import com.whisper.client.business.services.ClientServiceImpl;
import com.whisper.client.presentation.services.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.example.clientinterfaces.ClientServiceInt;
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
    private Button notificationBtn;
    @FXML
    private Button pendingBtn;

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
        homeBtn.setStyle("-fx-background-color: #597E52;");

    }

    @FXML
    public void onHomeClicked(Event event) {
        Parent root = null;

        if (!Objects.equals("homePane", mainPane.getCenter().getId())) {
            root = panes.get("homePane");

            mainPane.setCenter(root);
            homeBtn.setStyle("-fx-background-color: #597E52;");
            addContactBtn.setStyle("-fx-background-color: #trnasparent;");
            contactsBtn.setStyle("-fx-background-color: #trnasparent;");
            profileBtn.setStyle("-fx-background-color: #trnasparent;");
            notificationBtn.setStyle("-fx-background-color: #trnasparent;");
            pendingBtn.setStyle("-fx-background-color: #trnasparent;");

        }

    }

    @FXML
    public void onContactsClicked(Event event) {
        if (!Objects.equals("mainContactPane", mainPane.getCenter().getId())) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/contactView.fxml"));
                Parent root = fxmlLoader.load();

                contactController controller = fxmlLoader.getController();
                controller.setData(this);
                mainPane.setCenter(root);
                contactsBtn.setStyle("-fx-background-color: #597E52;");
                homeBtn.setStyle("-fx-background-color: #trnasparent;");
                addContactBtn.setStyle("-fx-background-color: #trnasparent;");
                profileBtn.setStyle("-fx-background-color: #trnasparent;");
                notificationBtn.setStyle("-fx-background-color: #trnasparent;");
                pendingBtn.setStyle("-fx-background-color: #trnasparent;");

            } catch (IOException e) {
                System.out.println("exception in main Controller class line 93");
            }
        }
    }

    @FXML
    public void onProfileClicked(Event event) {
        if (!Objects.equals("profilePane", mainPane.getCenter().getId())) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/profileView.fxml"));
                Parent root = fxmlLoader.load();

                mainPane.setCenter(root);
                profileBtn.setStyle("-fx-background-color: #597E52;");
                homeBtn.setStyle("-fx-background-color: #trnasparent;");
                addContactBtn.setStyle("-fx-background-color: #trnasparent;");
                contactsBtn.setStyle("-fx-background-color: #trnasparent;");
                notificationBtn.setStyle("-fx-background-color: #trnasparent;");
                pendingBtn.setStyle("-fx-background-color: #trnasparent;");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onSignOutClicked(Event event)  {
        Registry reg = null;
        try {
            SendContactsInvitationServiceInt serverRef = (SendContactsInvitationServiceInt) reg.lookup("SendContactsInvitationService");
            ClientServiceInt clientService = ClientServiceImpl.getInstance();
            serverRef.ServerUnRegister(clientService);

            //un register chats
            ClientService.getInstance().unRegisterChats();

            //unregister user
            ChattingService.getInstance().unRegisterUser();
            reg = LocateRegistry.getRegistry(8000);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }

    }

    @FXML
    public void onAddContactClicked(MouseEvent mouseEvent) {
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
            addContactBtn.setStyle("-fx-background-color: #597E52;");
            homeBtn.setStyle("-fx-background-color: #trnasparent;");
            contactsBtn.setStyle("-fx-background-color: #trnasparent;");
            profileBtn.setStyle("-fx-background-color: #trnasparent;");
            notificationBtn.setStyle("-fx-background-color: #trnasparent;");
            pendingBtn.setStyle("-fx-background-color: #trnasparent;");

        }
    }

    void navigateToHomeScreen() {
        Parent root = panes.get("homePane");
        mainPane.setCenter(root);
        homeBtn.setStyle("-fx-background-color: #597E52;");
        addContactBtn.setStyle("-fx-background-color: #trnasparent;");
        contactsBtn.setStyle("-fx-background-color: #trnasparent;");
        profileBtn.setStyle("-fx-background-color: #trnasparent;");
        notificationBtn.setStyle("-fx-background-color: #trnasparent;");
        pendingBtn.setStyle("-fx-background-color: #trnasparent;");

    }

    @FXML
    public void onNotificationsClicked(Event event) {
        if (!Objects.equals("notificationPane", mainPane.getCenter().getId())) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/notificationView.fxml"));
                Parent root = fxmlLoader.load();

                mainPane.setCenter(root);
                notificationBtn.setStyle("-fx-background-color: #597E52;");
                homeBtn.setStyle("-fx-background-color: #trnasparent;");
                addContactBtn.setStyle("-fx-background-color: #trnasparent;");
                profileBtn.setStyle("-fx-background-color: #trnasparent;");
                contactsBtn.setStyle("-fx-background-color: #trnasparent;");
                pendingBtn.setStyle("-fx-background-color: #trnasparent;");
            } catch (IOException e) {
                System.out.println("exception in main Controller class line 93");
            }
        }
    }

    @FXML
    public void onInvetationClicked(ActionEvent actionEvent) {
        //System.out.println("request clicked");
        if (!Objects.equals("requestPane", mainPane.getCenter().getId())) {
            //System.out.println("request clicked2");
            Parent root = SceneManager.getInstance().loadPane("requestView");

            mainPane.setCenter(root);
            pendingBtn.setStyle("-fx-background-color: #597E52;");
            homeBtn.setStyle("-fx-background-color: #trnasparent;");
            addContactBtn.setStyle("-fx-background-color: #trnasparent;");
            profileBtn.setStyle("-fx-background-color: #trnasparent;");
            contactsBtn.setStyle("-fx-background-color: #trnasparent;");
            notificationBtn.setStyle("-fx-background-color: #trnasparent;");
        }
    }
}