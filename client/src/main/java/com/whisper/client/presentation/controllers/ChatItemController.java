package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.business.services.ChattingService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.example.entities.RoomChat;
import org.example.entities.User;

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
    private RoomChat roomChat;

    private List<User> friendsOnChat = new ArrayList<>();

    private static HashMap<Integer,Parent> chatPanes = new HashMap<>();

    public void setData(BorderPane homePane, RoomChat roomChat){
        System.out.println("parent pane done");
        this.homePane = homePane;
        this.roomChat = roomChat;

        setRoomChatItemData(roomChat);
    }

    private void setRoomChatItemData(RoomChat roomChat) {
        ChattingService chattingService = ChattingService.getInstance();
        User friendUser = chattingService.getUserInCommonRoomChat(roomChat.getRoomChatId());
        friendsOnChat.add(friendUser);
        chatItemIName.setText(friendUser.getUserName());
        chatItemMode.setText(friendUser.getMode().name());
        //you need to convert byte array to image
       // chatItemImage.setImage(friendUser.getProfilePhoto());
    }

    @FXML
    public void initialize() {
        preLoad();
    }

    @FXML
    public void onChatItemClicked(Event event) {
        Parent node = chatPanes.get(roomChat.getRoomChatId());
        System.out.println(node);
        if(node == null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/roomChatView.fxml"));
                System.out.println("start Load");
                node = fxmlLoader.load();
                System.out.println("loaded");
                RoomChatController controller = fxmlLoader.getController();
                controller.setData(roomChat,friendsOnChat);
                chatPanes.put(roomChat.getRoomChatId(),node);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        homePane.setCenter(node);
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
}