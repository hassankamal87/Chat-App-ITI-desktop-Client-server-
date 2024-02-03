package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.business.services.ChattingService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.entities.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
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

    @FXML
    public void initialize() {
        System.out.println("initialize user");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize user");
        //you need to switch user id to current user id
        ChattingService.getInstance().getOrCreateRoomChat(1,2);
        List<RoomChat> roomChats = ChattingService.getInstance().getAllRoomChatsForUser(1);

        Node[] nodes = new Node[roomChats.size()];

        for (int i = 0; i < nodes.length; i++) {

            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/chatItemView.fxml"));
                nodes[i] = loader.load();
                ChatItemController controller = loader.getController();
                controller.setData(homePane, roomChats.get(i));
                chatList.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void onMouseEnteredSearchBtn(Event event) {
    }



}