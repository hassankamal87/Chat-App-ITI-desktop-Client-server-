package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.business.services.ChattingService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.example.entities.RoomChat;
import org.example.entities.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoomChatController
{
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
    private BorderPane chatBorderPane;
    @FXML
    private HTMLEditor messageEditor;
    @FXML
    private Button sendBtn;
    @FXML
    private ScrollPane messagesScrollPane;
    @FXML
    private VBox messageList;

    private ChattingService chattingService;
    private int roomChatID;
    private List<User> friendsOnChat = new ArrayList<>();


    public void setData(int roomChatID, List<User> friendsOnChat){
        this.roomChatID = roomChatID;
        this.friendsOnChat = friendsOnChat;
        nameText.setText(friendsOnChat.get(0).getUserName());
        modeText.setText(friendsOnChat.get(0).getMode().name());
    }

    @FXML
    public void initialize() {
        chattingService = ChattingService.getInstance();
    }

    @FXML
    public void onSendBtnClicked(Event event) {
        String htmlContent = messageEditor.getHtmlText();
        chattingService.sendMessage(1,3,htmlContent);
        messageEditor.setHtmlText("");

        Document doc = Jsoup.parse(htmlContent);
        String textContent = doc.text();
        if (!textContent.isEmpty()){
            appendMessageInList(htmlContent);
        }
    }

    @FXML
    public void onMouseEnteredSendBtn(Event event) {
    }

    private void appendMessageInList(String htmlContent) {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("views/messageItemView.fxml")));

            WebView messageWebView = (WebView) node.lookup("#messageWebView");
            WebEngine messageEngine = messageWebView.getEngine();
            messageEngine.loadContent(htmlContent);
            messagesScrollPane.setVvalue(1D);

            messageList.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessageFromList(String htmlContent) {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("views/messageItemViewReciever.fxml")));

            WebView messageWebView = (WebView) node.lookup("#messageWebView");
            WebEngine messageEngine = messageWebView.getEngine();
            messageEngine.loadContent(htmlContent);
            messagesScrollPane.setVvalue(1D);

            messageList.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}