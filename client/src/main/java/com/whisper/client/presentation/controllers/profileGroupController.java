package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.MyApp;
import com.whisper.client.business.services.ChattingService;
import com.whisper.client.business.services.ContactService;
import com.whisper.client.business.services.GroupService;
import com.whisper.client.presentation.services.DialogueManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.entities.RoomChat;
import org.example.entities.User;
import org.example.serverinterfaces.ChatServiceInt;
import org.example.serverinterfaces.EditGroupServiceInt;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Blob;
import java.sql.SQLException;
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

    int roomChatId;

    DialogueManager dialogueManager = DialogueManager.getInstance();
    GroupService groupService = new GroupService();

    @javafx.fxml.FXML
    public void onProfileClicked(Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            groupImage.setImage(image);
        }
    }

    private byte[] imageViewToByteArray(ImageView imageView) {
        Image image = imageView.getImage();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @javafx.fxml.FXML
    public void onSaveChangesClicked(ActionEvent actionEvent) {
        roomChat.setGroupName(groupName.getText());
        roomChat.setDescription(groupDescription.getText());
        byte[] imageByteArray = imageViewToByteArray(groupImage);

        roomChat.setPhotoBlob(imageByteArray); // Set the photo blob with the Blob object

        groupService.editGroup(roomChat);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myUser = MyApp.getInstance().getCurrentUser();
        otherContacts = contactService.getContacts(myUser.getUserId());

    }

    public void setData(int roomChatId){
        this.roomChatId = roomChatId;
        groupMembers = chattingService.getGroupMembers(roomChatId);
        System.out.println(groupMembers.size());
        roomChat = chattingService.getRoomChatByID(roomChatId);
        groupMembers.removeIf(user -> user.getUserId() == myUser.getUserId());
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

        for (int i=0; i<groupMembers.size(); i++){
            for (int j=0; j<otherContacts.size(); j++){
                System.out.println(groupMembers.size()+" "+otherContacts.size());
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
                System.out.println(user.getUserId()+" "+user.getUserName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        contactList.setItems(othersBox);
        showGroupData();
    }

    String defaultImagePath = "/com/whisper/client/images/profile.png";
    Image defaultImage = new Image(getClass().getResourceAsStream(defaultImagePath));

    private void showGroupData() {
        groupName.setText(roomChat.getGroupName());
        groupDescription.appendText(roomChat.getDescription());
//        groupImage.setImage(new Image(new ByteArrayInputStream(roomChat.getPhotoBlob().toString().getBytes())));
        byte[] photoBlob = roomChat.getPhotoBlob();
        if (photoBlob != null) {

                Image image = new Image(new ByteArrayInputStream(photoBlob));
                groupImage.setImage(image);


        }else {
            groupImage.setImage(defaultImage);
        }
    }

    @javafx.fxml.FXML
    public void onClickRemove(ActionEvent actionEvent) {
        System.out.println(groupMembers.size());
        int userIndex = groupMembersList.getSelectionModel().getSelectedIndex();
        if (groupMembersList.getSelectionModel().getSelectedIndex() == -1){
            dialogueManager.showInformationDialog("Button Disabled", "No member selected!");
            return;
        }
        User removeUser= groupMembers.get(userIndex);
        chattingService.removeGroupMember(roomChatId, removeUser.getUserId());
        System.out.println(removeUser.getUserId());
//        contactList.setItems(othersBox);
        HBox removedBox = boxes.remove(userIndex);
        otherContacts.add(removeUser);
        groupMembers.remove(userIndex);
        othersBox.add(removedBox);
        boxes.remove(removedBox);
    }

    @javafx.fxml.FXML
    public void onClickAdd(ActionEvent actionEvent) {
        int userIndex = contactList.getSelectionModel().getSelectedIndex();
        if (contactList.getSelectionModel().getSelectedIndex() == -1){
            System.out.println("here");
            dialogueManager.showInformationDialog("Button Disabled", "No contact selected!");
            return;
        }
        HBox box = (HBox) contactList.getSelectionModel().getSelectedItem();

        User addUser = otherContacts.get(userIndex);

        System.out.println(addUser.getUserId());
        chattingService.addGroupMember(roomChatId, addUser.getUserId());
        groupMembers.add(addUser);
        otherContacts.remove(userIndex);
        boxes.add(box);
        othersBox.remove(box);
    }
}
