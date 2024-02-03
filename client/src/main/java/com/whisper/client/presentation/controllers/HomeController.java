package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.entities.RoomChat;
import org.example.entities.Type;

import java.io.IOException;
import java.net.URL;
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Node[] nodes = new Node[5];

        for (int i = 0; i < nodes.length; i++) {

            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/chatItemView.fxml"));
                nodes[i] = loader.load();
                ChatItemController controller = loader.getController();
                controller.setData(homePane, new RoomChat(i,null,true,"mohamed"+i,null,1,"desc", Type.individual));
                chatList.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//             //   nodes[i] = loader.load();
//             //   chatList.getChildren().add(nodes[i]);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

//                nodes[i] = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("views/chatItemView.fxml")));

        }
    }

    @FXML
    public void onMouseEnteredSearchBtn(Event event) {
    }



}