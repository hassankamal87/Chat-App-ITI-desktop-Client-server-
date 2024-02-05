package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.business.services.ChattingService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import org.example.entities.RoomChat;
import org.example.entities.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatItemController
{
    @FXML
    private AnchorPane chatItemContainer;
    @FXML
    private HBox chatItem;
    @FXML
    private ImageView chatItemImage;
    @FXML
    private Label chatItemIName;
    @FXML
    private Label chatItemMode;

    private BorderPane homePane;
    private int roomChatId;
    private List<User> friendsOnChat = new ArrayList<>();

    private static HashMap<Integer,Parent> chatPanes = new HashMap<>();
    @FXML
    private Label email;

    public void setData(BorderPane homePane, int roomChatID){
        System.out.println("parent pane done");
        this.homePane = homePane;
        this.roomChatId = roomChatID;

        setRoomChatItemData(roomChatID);
    }

    private void setRoomChatItemData(int roomChatId) {
        ChattingService chattingService = ChattingService.getInstance();
        User friendUser = chattingService.getUserInCommonRoomChat(roomChatId);
        friendsOnChat.add(friendUser);
        chatItemIName.setText(friendUser.getUserName());
        chatItemMode.setText(friendUser.getMode().name());
        email.setText(friendUser.getEmail());
        chatItemImage.setImage(new Image(new ByteArrayInputStream(friendUser.getProfilePhoto())));
        makeImageRounded(chatItemImage);
    }

    private void makeImageRounded(ImageView chatItemImage) {
        Circle clip = new Circle(chatItemImage.getFitWidth() / 2.6, chatItemImage.getFitHeight() / 1.8, chatItemImage.getFitWidth() /3);
        chatItemImage.setClip(clip);
        chatItemImage.setPreserveRatio(true);
    }

    @FXML
    public void initialize() {
        preLoad();
    }

    @FXML
    public void onChatItemClicked(Event event) {
        openChat();
    }



    //work around to pre load and make loading faster.
    private void preLoad()  {
        Parent node = chatPanes.get(-1);
        if(node == null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/roomChatView.fxml"));
                node = fxmlLoader.load();
                chatPanes.put(-1,node);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void openChat(){
        Parent node = chatPanes.get(roomChatId);
        System.out.println(node);
        if(node == null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/roomChatView.fxml"));
                System.out.println("start Load");
                node = fxmlLoader.load();
                System.out.println("loaded");
                RoomChatController controller = fxmlLoader.getController();
                controller.setData(roomChatId,friendsOnChat);
                chatPanes.put(roomChatId,node);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        homePane.setCenter(node);
    }


    @FXML
    public void onChatItemEntered(MouseEvent mouseEvent) {
        chatItemContainer.setStyle("-fx-background-color: #698B62");

    }

    @FXML
    public void onChatItemExit(MouseEvent mouseEvent) {
        chatItemContainer.setStyle("-fx-background-color: #FFE0BF");
    }
}