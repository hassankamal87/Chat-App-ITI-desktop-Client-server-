package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.MyApp;
import com.whisper.client.business.services.ChattingService;
import com.whisper.client.business.services.ClientService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.example.entities.Message;
import org.example.entities.RoomChat;
import org.example.entities.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoomChatController implements ReceiveMessageInterface{
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
    private List<User> friendsOnChat;


    public void setData(int roomChatID, List<User> friendsOnChat){
        this.roomChatID = roomChatID;
        this.friendsOnChat = friendsOnChat;
        nameText.setText(friendsOnChat.get(0).getUserName());
        modeText.setText(friendsOnChat.get(0).getMode().name());

        try {
            ClientService.getInstance().registerChat(roomChatID,this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        List<Message> messages = chattingService.getAllMessagesForRoomChat(roomChatID);

        showOldMessages(messages);
    }

    @FXML
    public void initialize() {
        chattingService = ChattingService.getInstance();
    }

    @FXML
    public void onSendBtnClicked(Event event) {
        String htmlContent = messageEditor.getHtmlText();
        chattingService.sendMessage(MyApp.getInstance().getCurrentUser().getUserId(),roomChatID,htmlContent);
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
            ImageView myImage = (ImageView) node.lookup("#ImageView");
            WebEngine messageEngine = messageWebView.getEngine();
          //  myImage.setImage(MyApp.getInstance().getCurrentUser().getProfilePhoto());
            messageEngine.loadContent(htmlContent);
            messagesScrollPane.setVvalue(1D);

            messageList.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMessageFromList(Message message) {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("views/messageItemViewReciever.fxml")));

            WebView messageWebView = (WebView) node.lookup("#messageWebView");
            Label nameLabel = (Label) node.lookup("#nameLabel");

            WebEngine messageEngine = messageWebView.getEngine();
            messageEngine.loadContent(message.getBody());
            String friendName = friendsOnChat.stream().filter(friend->friend.getUserId() == message.getFromUserId()).findFirst().get().getUserName();
            nameLabel.setText(friendName);
            messagesScrollPane.setVvalue(1D);

            messageList.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendMessageInList(Message message) {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("views/messageItemView.fxml")));

            WebView messageWebView = (WebView) node.lookup("#messageWebView");
            ImageView myImage = (ImageView) node.lookup("#ImageView");
            WebEngine messageEngine = messageWebView.getEngine();
            //  myImage.setImage(MyApp.getInstance().getCurrentUser().getProfilePhoto());
            messageEngine.loadContent(message.getBody());
            messagesScrollPane.setVvalue(1D);

            messageList.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showOldMessages(List<Message> messages) {
        for (Message message : messages){
            if(message.getFromUserId() == MyApp.getInstance().getCurrentUser().getUserId()){
                appendMessageInList(message);
            }else{
                receiveMessageFromList(message);
            }
        }
    }

}