package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.MyApp;
import com.whisper.client.business.services.ChattingService;
import com.whisper.client.business.services.ContactService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.entities.RoomChat;
import org.example.entities.User;
import org.example.serverinterfaces.ChatServiceInt;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

public class profileGroupController implements Initializable {
    @javafx.fxml.FXML
    private ImageView groupImage;
    @javafx.fxml.FXML
    private Label userNameLabel;
    @javafx.fxml.FXML
    private TextField groupName;
    @javafx.fxml.FXML
    private TextArea groupDescription;
    @javafx.fxml.FXML
    private Button saveChanges;
    @javafx.fxml.FXML
    private ListView groupMembersList;
    @javafx.fxml.FXML
    private Button removeBtn;
    @javafx.fxml.FXML
    private Button addBtn;
    @javafx.fxml.FXML
    private ListView contactList;
    ObservableList<HBox> boxes = FXCollections.observableArrayList();
    ObservableList<HBox> othersBox = FXCollections.observableArrayList();
    GroupMemberController groupMemberController;
    List<User> groupMembers;
    List<User> otherContacts;
    ContactService contactService = new ContactService();

    ChattingService chattingService = ChattingService.getInstance();
    User myUser = null;
    HBox hBox;
    RoomChat roomChat;

//    List<User> users = new ArrayList<>(users());


    @javafx.fxml.FXML
    public void onProfileClicked(Event event) {

    }

    @javafx.fxml.FXML
    public void onSaveChangesClicked(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myUser = MyApp.getInstance().getCurrentUser();
        otherContacts = contactService.getContacts(myUser.getUserId());
//        otherContacts = contactService.getContacts(myUser.getUserId());
        groupMembers = chattingService.getGroupMembers(5);
        System.out.println(groupMembers.size());
        roomChat = chattingService.getRoomChatByID(5);


        for (User user: groupMembers){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/groupMemberView.fxml"));
            try{
                hBox = fxmlLoader.load();
                groupMemberController = fxmlLoader.getController();
                groupMemberController.setData(user);
                boxes.add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        groupMembersList.setItems(boxes);

//        System.out.println(otherContacts.size());
//        otherContacts.removeIf(contact -> groupMembers.contains(contact));
//        otherContacts.removeAll(groupMembers);
//
//        // Remove elements from otherContacts that exist in groupMembers
//        otherContacts.removeAll(groupMembers);

        for (int i=0; i<groupMembers.size(); i++){
            for (int j=0; j<otherContacts.size(); j++){
                if (groupMembers.get(i).getUserId() == otherContacts.get(j).getUserId())
                    otherContacts.remove(j);
            }
        }

        for (User user: otherContacts){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/groupMemberView.fxml"));
            try{
                hBox = fxmlLoader.load();
                groupMemberController = fxmlLoader.getController();
                groupMemberController.setData(user);
                othersBox.add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        contactList.setItems(othersBox);
        showGroupData();
    }

    public void deleteMember(){

    }

    private void showGroupData() {
        groupName.setText(roomChat.getGroupName());
        groupDescription.appendText(roomChat.getDescription());
//        groupImage.setImage(new Image(new ByteArrayInputStream(roomChat.getPhotoBlob()));
    }

    @javafx.fxml.FXML
    public void onClickRemove(ActionEvent actionEvent) {
        int userIndex = groupMembersList.getSelectionModel().getSelectedIndex();
        HBox removedBox = boxes.remove(userIndex);
//        System.out.println(userIndex);
//        System.out.println(groupMembers.size());
//        System.out.println(groupMembers.size());
        chattingService.removeGroupMember(5, groupMembers.get(userIndex).getUserId());
        groupMembers.remove(userIndex);
        othersBox.add(removedBox);
        contactList.setItems(othersBox);
        otherContacts.add(groupMembers.get(userIndex));
    }

    @javafx.fxml.FXML
    public void onClickAdd(ActionEvent actionEvent) {
        int userIndex = contactList.getSelectionModel().getSelectedIndex();
        HBox box = (HBox) contactList.getSelectionModel().getSelectedItem();
        boxes.add(box);
        othersBox.remove(box);
        chattingService.addGroupMember(5, otherContacts.get(userIndex).getUserId());
    }
}
