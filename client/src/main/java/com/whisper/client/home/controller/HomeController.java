package com.whisper.client.home.controller;

import com.whisper.client.HelloApplication;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
    private Button sendBtn;
    @FXML
    private BorderPane chatBorderPane;

    @FXML
    private VBox messageList;
    @FXML
    private HTMLEditor messageEditor;
    @FXML
    private ScrollPane messagesScrollPane;

    @FXML
    public void initialize() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Node[] nodes = new Node[5];

        for (int i = 0; i < nodes.length; i++) {
            try {
                nodes[i] = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("home/view/chatItemView.fxml")));

                nodes[i].setOnMouseEntered(event -> {

                });

                nodes[i].setOnMouseExited(event -> {

                });

                chatList.getChildren().add(nodes[i]);

            } catch (IOException e) {
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

    private void appendMessageInList(String htmlContent) {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("home/view/messageItemView.fxml")));

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