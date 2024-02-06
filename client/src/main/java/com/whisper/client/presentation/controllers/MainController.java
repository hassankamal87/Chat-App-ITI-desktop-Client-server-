package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.MyApp;
import com.whisper.client.business.services.ChattingService;
import com.whisper.client.business.services.ClientService;
import com.whisper.client.business.services.ClientServiceImpl;
import com.whisper.client.presentation.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.example.clientinterfaces.ClientServiceInt;
import org.example.serverinterfaces.SendContactsInvitationServiceInt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

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
            Parent root = SceneManager.getInstance().loadPane("profileView");
            mainPane.setCenter(root);
            profileBtn.setStyle("-fx-background-color: #597E52;");
            homeBtn.setStyle("-fx-background-color: #trnasparent;");
            addContactBtn.setStyle("-fx-background-color: #trnasparent;");
            contactsBtn.setStyle("-fx-background-color: #trnasparent;");
            notificationBtn.setStyle("-fx-background-color: #trnasparent;");
            pendingBtn.setStyle("-fx-background-color: #trnasparent;");
        }
    }

    @FXML
    public void onSignOutClicked(Event event) throws RemoteException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry(8000);
        SendContactsInvitationServiceInt serverRef = (SendContactsInvitationServiceInt) reg.lookup("SendContactsInvitationService");
        ClientServiceInt clientService = ClientServiceImpl.getInstance();
        serverRef.ServerUnRegister(clientService);
        SceneManager.getInstance().clearPanes();

        // Unregister chats
        ClientService.getInstance().unRegisterChats();

        // Unregister user
        ChattingService.getInstance().unRegisterUser();

        try {
            File file = new File("userInfo.properties");
            if (file.exists()) {
                Properties props = new Properties();
                props.load(new FileInputStream(file));

                props.setProperty("password", "");
//                props.setProperty("phoneNumber", "");
                props.setProperty("rememberMe", "false");

                props.store(new FileOutputStream(file), "User Properties");

                System.out.println("User signed out successfully.");
                SceneManager.getInstance().loadView("signInView");
                MyApp.getInstance().setCurrentUser(null);
                System.out.println("6/2 user: " + MyApp.getInstance().getCurrentUser());
            } else {
                System.out.println("userInfo.properties file does not exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    public void onInvitationClicked(ActionEvent actionEvent) {

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