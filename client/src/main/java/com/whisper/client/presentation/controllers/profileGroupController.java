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
        try {
            Blob imageBlob = new SerialBlob(imageByteArray);
            roomChat.setPhotoBlob(imageBlob); // Set the photo blob with the Blob object
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        groupService.editGroup(roomChat);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myUser = MyApp.getInstance().getCurrentUser();
        otherContacts = contactService.getContacts(myUser.getUserId());
//        otherContacts = contactService.getContacts(myUser.getUserId());
        groupMembers = chattingService.getGroupMembers(25);
        System.out.println(groupMembers.size());
        roomChat = chattingService.getRoomChatByID(25);

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
                if (groupMembers.get(i).getUserId() == otherContacts.get(j).getUserId())
                    otherContacts.remove(j);
                if (groupMembers.get(i).getUserId() == myUser.getUserId())
                    groupMembers.remove(i);
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

    String defaultImagePath = "/com/whisper/client/images/profile.png";
    Image defaultImage = new Image(getClass().getResourceAsStream(defaultImagePath));

    private void showGroupData() {
        groupName.setText(roomChat.getGroupName());
        groupDescription.appendText(roomChat.getDescription());
//        groupImage.setImage(new Image(new ByteArrayInputStream(roomChat.getPhotoBlob().toString().getBytes())));
        Blob photoBlob = roomChat.getPhotoBlob();
        if (photoBlob != null) {
            try {
                InputStream inputStream = photoBlob.getBinaryStream();
                Image image = new Image(inputStream);
                groupImage.setImage(image);
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }

        }else {
            groupImage.setImage(defaultImage);
        }
    }

    @javafx.fxml.FXML
    public void onClickRemove(ActionEvent actionEvent) {
//        System.out.println(groupMembers.size());
        int userIndex = groupMembersList.getSelectionModel().getSelectedIndex();
        if (groupMembersList.getSelectionModel().getSelectedIndex() == -1){
            dialogueManager.showInformationDialog("Button Disabled", "No member selected!");
            return;
        }
        chattingService.removeGroupMember(5, groupMembers.get(userIndex).getUserId());
        System.out.println(groupMembers.size());
//        contactList.setItems(othersBox);
        HBox removedBox = boxes.remove(userIndex);
        otherContacts.add(groupMembers.get(userIndex));
        groupMembers.remove(userIndex);
        othersBox.add(removedBox);
    }

    @javafx.fxml.FXML
    public void onClickAdd(ActionEvent actionEvent) {
        int userIndex = contactList.getSelectionModel().getSelectedIndex();
        if (contactList.getSelectionModel().getSelectedIndex() == -1){
            dialogueManager.showInformationDialog("Button Disabled", "No contact selected!");
            return;
        }
        HBox box = (HBox) contactList.getSelectionModel().getSelectedItem();
        chattingService.addGroupMember(5, otherContacts.get(userIndex).getUserId());
        groupMembers.add(otherContacts.get(userIndex));
        boxes.add(box);
        othersBox.remove(box);
    }
}
