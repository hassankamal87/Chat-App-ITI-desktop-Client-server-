package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.MyApp;
import com.whisper.client.business.services.ChattingService;
import com.whisper.client.business.services.ClientService;
import javafx.application.Platform;
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
import java.rmi.RemoteException;
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

    public HashMap<Integer, ChatItemController> itemsControllers = new HashMap<>();

    @FXML
    public void initialize() {
        System.out.println("initialize user");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize user");
        initializeRoomChats();
        ChattingService.getInstance().registerHandlingInterface(this);
        try {
            ClientService.getInstance().registerHomePane(this);
        } catch (RemoteException e) {
            System.out.println("cannot register user class Home Controller 58");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }

    private void initializeRoomChats() {
        List<RoomChat> roomChats = ChattingService.getInstance().getAllRoomChatsForUser(MyApp.getInstance().getCurrentUser().getUserId());

        for (RoomChat roomChat : roomChats) {
            loadChatItemController(roomChat);
        }
    }

    private void loadChatItemController(RoomChat roomChat) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/chatItemView.fxml"));
            Node node = loader.load();
            ChatItemController controller = loader.getController();
            controller.setData(homePane, roomChat.getRoomChatId());
            itemsControllers.put(roomChat.getRoomChatId(), controller);
            chatList.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addRoomChat(int roomChatID) {
        if (!itemsControllers.containsKey(roomChatID)) {
            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/chatItemView.fxml"));
                Parent node = loader.load();
                ChatItemController controller = loader.getController();
                controller.setData(homePane, roomChatID);
                controller.openChat();
                itemsControllers.put(roomChatID, controller);
                chatList.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
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