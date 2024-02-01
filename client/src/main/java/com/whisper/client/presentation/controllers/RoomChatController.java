package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
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

    private RoomChat roomChat;

    public void setData(RoomChat roomChat){
        this.roomChat = roomChat;
        nameText.setText(roomChat.getGroupName());
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void onSendBtnClicked(Event event) {
        String htmlContent = messageEditor.getHtmlText();
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


}