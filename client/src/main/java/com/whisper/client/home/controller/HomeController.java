package com.whisper.client.home.controller;

import com.whisper.client.HelloApplication;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private AnchorPane homePane;
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
    private AnchorPane roomChatPane;
    @FXML
    private ImageView personalImage;
    @FXML
    private Text nameText;
    @FXML
    private Text modeText;
    @FXML
    private Button callBtn;
    @FXML
    private AnchorPane textRichContainer;
    @FXML
    private TextField messageTextArea;
    @FXML
    private Button sendBtn;
    @FXML
    private ListView messagesListView;

    @FXML
    public void initialize() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Node[] nodes = new Node[15];

        for(int i=0;i<nodes.length;i++){
            try {
                nodes[i] = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("home/view/chatItemView.fxml")));

                nodes[i].setOnMouseEntered(event -> {

                });

                nodes[i].setOnMouseExited(event -> {

                });

                chatList.getChildren().add(nodes[i]);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onMouseEnteredSearchBtn(Event event) {
    }

    @FXML
    public void onMouseEnteredSendBtn(Event event) {
    }
}