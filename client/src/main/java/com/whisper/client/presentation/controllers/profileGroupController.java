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
//        myUser = MyApp.getInstance().getCurrentUser();
//        otherContacts = contactService.getContacts(myUser.getUserId());
        otherContacts = contactService.getContacts(6);
//            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 8000);
//            ChatServiceInt chatService = (ChatServiceInt) reg.lookup("ChatService");
        groupMembers = chattingService.getGroupMembers(4);
        roomChat = chattingService.getRoomChatByID(4);


        for (int i=0; i<groupMembers.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/groupMemberView.fxml"));
            try{
                hBox = fxmlLoader.load();
                groupMemberController = fxmlLoader.getController();
                groupMemberController.setData(groupMembers.get(i));
                boxes.add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            groupMembersList.setItems(boxes);
        }

        for (int i=0; i<otherContacts.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/groupMemberView.fxml"));
            try{
                hBox = fxmlLoader.load();
                groupMemberController = fxmlLoader.getController();
                groupMemberController.setData(otherContacts.get(i));
                boxes.add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            groupMembersList.setItems(boxes);
        }
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
        boxes.remove(userIndex);
        groupMembers.remove(userIndex);
        chattingService.removeGroupMember(roomChat.getRoomMembers().get(userIndex));
    }

    @javafx.fxml.FXML
    public void onClickAdd(ActionEvent actionEvent) {
    }
}
