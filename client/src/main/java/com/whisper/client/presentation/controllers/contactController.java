package com.whisper.client.presentation.controllers;
import com.whisper.client.HelloApplication;
import com.whisper.client.MyApp;
import com.whisper.client.business.services.ChattingService;
import com.whisper.client.business.services.ContactService;
import com.whisper.client.presentation.services.DialogueManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    Set<User> finalGroupChatUserIds = new HashSet<>();
    @FXML
    private Button addFriendsToAGroupButton;
    StringBuilder usersNames = new StringBuilder();
    Map<HBox, Boolean> usersAddedToGroupStatus = new HashMap<>();


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
                        handleCreatingGroupChat(contacts.get(finalI),hBox);
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
    private void handleCreatingGroupChat(User user,HBox node) {
        createGroupButton.setDisable(false);
        boolean added = finalGroupChatUserIds.add(user);
        if (added) {
            usersNames.append(user.getUserName()).append("\n");
            node.setStyle("-fx-background-color: #597E52");
        }else{
            finalGroupChatUserIds.remove(user);
            node.setStyle("-fx-background-color: #F3F3E1");
            usersNames.delete(0, usersNames.length());
            finalGroupChatUserIds.forEach(e->{
                usersNames.append(e.getUserName()).append("\n");
            });
            if(finalGroupChatUserIds.isEmpty()){
                createGroupButton.setDisable(true);
            }
        }
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

    private void startGroup(List<User> contacts){
        contacts.forEach(e->{
            System.out.println(e.getUserName());
        });

        ChattingService.getInstance().createGroupChat(
                MyApp.getInstance().getCurrentUser().getUserId(),contacts
        );
        mainController.navigateToHomeScreen();
    }
    private List<User> contacts(){
        ContactService contactService= new ContactService();
        List<User>contacts =contactService.getContacts(MyApp.getInstance().getCurrentUser().getUserId());
        return contacts;
    }
    @FXML
    public void onCreateGroupChatClicked(ActionEvent actionEvent) {
        startGroup(finalGroupChatUserIds.stream().toList());
        System.out.println("Group Chat: " + finalGroupChatUserIds);
        clearGroupChat();
    }
    @FXML
    public void addFriendsToAGroupClicked(ActionEvent actionEvent) {
        if(!cretaingGroupChat) {
            // enable creating group chat
            addFriendsToAGroupButton.setStyle("-fx-background-color: #597E52");
            cretaingGroupChat = true;
        } else {
            // disable creating group chat
            clearGroupChat();
            System.out.println("Group Chat: " + finalGroupChatUserIds);
        }
    }
    private void clearGroupChat() {
        createGroupButton.setDisable(true);
        addFriendsToAGroupButton.setStyle("-fx-background-color: #BDA164");
        cretaingGroupChat = false;
        finalGroupChatUserIds.clear();
        usersNames.setLength(0);
    }
    @FXML
    public void onShowGroupMembersClicked(ActionEvent actionEvent) {
        DialogueManager dialogueManager = DialogueManager.getInstance();
        if(!finalGroupChatUserIds.isEmpty()) {
            dialogueManager.showInformationDialog("Group Members", usersNames.toString());
        } else {
            dialogueManager.showInformationDialog("No Members", "No members in the group chat");
        }
    }
}