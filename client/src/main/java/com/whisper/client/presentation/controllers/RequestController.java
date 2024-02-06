package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.MyApp;
import com.whisper.client.business.services.ChattingService;
import com.whisper.client.business.services.ContactService;

import com.whisper.client.business.services.NotificationService;
import com.whisper.client.business.services.UserSearchService;
import com.whisper.client.presentation.services.DialogueManager;
import com.whisper.client.presentation.services.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.entities.Notification;
import org.example.entities.User;
import org.example.serverinterfaces.SendContactsInvitationServiceInt;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RequestController implements Initializable
{


    @FXML
    private ListView contactsLayout;

    @FXML
    private ScrollPane scroll ;


    ContactService contactService= new ContactService();
    private MainController mainController;
    //ObservableList<HBox> boxes;
    requestItemController nic;
    HBox hBox;
    List<User> requests = new ArrayList<>(requests());

    ObservableList<HBox> boxes = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);



        for(int i=0;i<requests.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/requestItemView.fxml"));

            try {
                hBox = fxmlLoader.load();
                nic = fxmlLoader.getController();
                nic.setData(requests.get(i));
                boxes.add(hBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        contactsLayout.setItems(boxes);
        for (int i=0;i<boxes.size();i++){
            Button b = (Button) boxes.get(i).getChildren().get(3);
            Button acc = (Button) boxes.get(i).getChildren().get(2);
            int finalI = i;
            b.setOnAction(event->deleteAction(event, finalI));
            acc.setOnAction(event -> acceptAction(event,finalI));
        }
    }

    private void acceptAction(ActionEvent event, int i) {
        Button deleteButton = (Button) event.getSource();
        HBox notificationBox = (HBox) deleteButton.getParent();
        boxes.remove(notificationBox);
        requests().remove(i);

        contactService.addContact(MyApp.getInstance().getCurrentUser().getUserId(),requests().get(i).getUserId());
        contactService.deleleteRequest(MyApp.getInstance().getCurrentUser().getUserId(),requests().get(i).getUserId());

//        List<String> phone = new ArrayList<>();
//        phone.add(MyApp.getInstance().getCurrentUser().getPhoneNumber());
//        UserSearchService userSearchService = new UserSearchService();
//        userSearchService.sendInvitation(requests().get(i).getUserId(),phone);


    }

    private void deleteAction(Event event, int i) {
        Button deleteButton = (Button) event.getSource();
        HBox notificationBox = (HBox) deleteButton.getParent();
        boxes.remove(notificationBox);
        requests().remove(i);
        contactService.deleleteRequest(MyApp.getInstance().getCurrentUser().getUserId(),requests().get(i).getUserId());

    }



    private List<User> requests(){
        ContactService contactService= new ContactService();
        List<User>requests =contactService.getRequests(MyApp.getInstance().getCurrentUser().getUserId());
        return requests;
    }








}