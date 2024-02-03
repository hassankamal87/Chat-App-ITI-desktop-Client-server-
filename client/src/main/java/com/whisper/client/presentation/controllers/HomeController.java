package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.business.services.ChattingService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.entities.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable, HandlingChatInterface {
    @FXML
    private AnchorPane chatsPane;
    @FXML
    private TextField searchTF;
    @FXML
    private Button searchBtn;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox chatList;
    @FXML
    private BorderPane homePane;

    private HashMap<Integer,ChatItemController> itemsControllers = new HashMap<>();

    @FXML
    public void initialize() {
        System.out.println("initialize user");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize user");
        //you need to switch user id to current user id
        //ChattingService.getInstance().getOrCreateRoomChat(1,5);

        initializeRoomChats();
        ChattingService.getInstance().registerHandlingInterface(this);
    }

    private void initializeRoomChats() {
        List<RoomChat> roomChats = ChattingService.getInstance().getAllRoomChatsForUser(1);
        Node[] nodes = new Node[roomChats.size()];

        for (int i = 0; i < nodes.length; i++) {

            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/chatItemView.fxml"));
                nodes[i] = loader.load();
                ChatItemController controller = loader.getController();
                controller.setData(homePane, roomChats.get(i).getRoomChatId());
                itemsControllers.put(roomChats.get(i).getRoomChatId(),controller);
                chatList.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addRoomChat(int roomChatID){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/chatItemView.fxml"));
            Parent node = loader.load();
            ChatItemController controller = loader.getController();
            controller.setData(homePane, roomChatID);
            controller.openChat();
            itemsControllers.put(roomChatID,controller);
            chatList.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openExistChat(int roomChatID) {
        ChatItemController controller = itemsControllers.get(roomChatID);
        controller.openChat();
    }

    @FXML
    public void onMouseEnteredSearchBtn(Event event) {
    }


}