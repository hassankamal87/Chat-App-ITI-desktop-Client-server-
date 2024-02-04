package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.MyApp;
import com.whisper.client.business.services.ChattingService;
import com.whisper.client.business.services.ContactService;

import com.whisper.client.presentation.services.DialogueManager;
import com.whisper.client.presentation.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.entities.User;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class contactController implements Initializable
{

    private BorderPane rootPane;
    @FXML
    private VBox contactsLayout;

    @FXML
    private ScrollPane scroll ;

    @FXML
    private AnchorPane mainContactPane;

    private MainController mainController;
    private boolean cretaingGroupChat = false;
    HBox hBox = null;
    @FXML
    private Button createGroupButton;
    List<User> contacts = new ArrayList<>(contacts());

    Set<Integer> groupChatUserIds = new HashSet<>();
    @FXML
    private Button addFriendsToAGroupButton;
    StringBuilder usersNames = new StringBuilder();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        createGroupButton.setDisable(true);

        List<User> contacts = new ArrayList<>(contacts());
        for(int i=0;i<contacts.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/contactItemView.fxml"));

            try{
                HBox hBox=fxmlLoader.load();
                contactItemController cic = fxmlLoader.getController();
                cic.setData(contacts.get(i));
                int finalI = i;
                hBox.setOnMouseClicked(event -> {
                    if(cretaingGroupChat) {
                        handleCreatingGroupChat(contacts.get(finalI));
                    } else {
                        startChat(contacts.get(finalI));
                    }
                });
            contactsLayout.getChildren().add(hBox);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void handleCreatingGroupChat(User user) {
        createGroupButton.setDisable(false);
        usersNames.append(user.getUserName()).append("\n");
        groupChatUserIds.add(user.getUserId());

        System.out.println(groupChatUserIds);
    }


    public void setData(MainController mainController){
        this.mainController = mainController;
    }
    private void startChat(User contact) {
        System.out.println(contact.getUserId());
        ChattingService.getInstance().getOrCreateRoomChat
                (MyApp.getInstance().getCurrentUser().getUserId(),contact.getUserId());
        mainController.navigateToHomeScreen();
    }

    private List<User> contacts(){
        ContactService contactService= new ContactService();
        List<User>contacts =contactService.getContacts(MyApp.getInstance().getCurrentUser().getUserId());
        return contacts;
    }

    @FXML
    public void onCreateGroupChatClicked(ActionEvent actionEvent) {
        System.out.println("Group Chat Created with members: " + groupChatUserIds);
        clearGroupChat();
    }

    @FXML
    public void addFriendsToAGroupClicked(ActionEvent actionEvent) {
        if(!cretaingGroupChat) {
            // enable creating group chat
            addFriendsToAGroupButton.setStyle("-fx-background-color: #2e2e2e");
            cretaingGroupChat = true;

        } else {
            // disable creating group chat
            clearGroupChat();
            System.out.println("Group Chat: " + groupChatUserIds);
        }
    }

    private void clearGroupChat() {
        createGroupButton.setDisable(true);
        addFriendsToAGroupButton.setStyle("-fx-background-color: #BDA164");
        cretaingGroupChat = false;
        groupChatUserIds.clear();
        usersNames.setLength(0);

    }

    public void onShowGroupMembersClicked(ActionEvent actionEvent) {
        if(!groupChatUserIds.isEmpty()) {
            DialogueManager.showInformationDialog("Group Members", usersNames.toString());
        } else {
            DialogueManager.setData("Group Members", "No Members", "No members in the group chat");
        }
    }
}