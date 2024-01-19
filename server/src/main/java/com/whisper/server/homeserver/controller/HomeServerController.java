package com.whisper.server.homeserver.controller;

import com.whisper.server.HelloApplication;
import com.whisper.server.announcer.controller.AnnouncerHomeController;
import com.whisper.server.model.Contact;
import com.whisper.server.model.Notification;
import com.whisper.server.model.PendingRequest;
import com.whisper.server.model.User;
import com.whisper.server.services.db.dao.Dao;
import com.whisper.server.services.db.dao.DaoInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeServerController {

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Label cantDoAnyThingLabel;
    @FXML
    private StackPane toggleSwitch;
    private boolean isSwitchOn = false;
    private Rectangle rectangle;
    private Circle circle;
    @FXML
    private BorderPane mainNavigatorPane;
    private Parent announcementPane = null;

    public void initialize() {
        gettingPanes();
        disableButtons();
        setupToggleSwitch();
        setupToggleSwitchHandler();
    }
    private void gettingPanes(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication
                    .class.getResource("announcer/view/announcerHomeView.fxml"));
            announcementPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupToggleSwitch() {
        rectangle = new Rectangle(60, 31);
        rectangle.setArcWidth(30);
        rectangle.setArcHeight(30);
        rectangle.setFill(Color.RED);

        circle = new Circle(15);
        circle.setTranslateX(15);
        circle.setFill(Color.WHITE);

        toggleSwitch.getChildren().addAll(rectangle, circle);
    }

    private void setupToggleSwitchHandler() {
        toggleSwitch.setOnMouseClicked(event -> {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.2), circle);
            if (circle.getTranslateX() <= -15) {
                translateTransition.setToX(15);
                rectangle.setFill(Color.RED);
                isSwitchOn = false;
            } else {
                translateTransition.setToX(-15);
                rectangle.setFill(Color.GREEN);
                isSwitchOn = true;
            }
            translateTransition.play();
            handleToggleSwitchChange(isSwitchOn);

            DaoInterface testDao = Dao.getInstance();
            try {
                //getUsers test
//                List<User> users = testDao.getUsers();
//                users.forEach(user -> {
//                    System.out.println(user.getUserName());
//                    System.out.println(user.getUserId());
//                    System.out.println(user.getCountry());
//                    System.out.println(user.getGender());
//                    System.out.println(user.getPhoneNumber());
//                    System.out.println(user.getPassword());
//                    System.out.println(user.getEmail());
//                    System.out.println("......................");
//                });

                //getUserStatus test
                int userId = 1;
//                testDao.getUserStatus(userId);
//                System.out.println("Status: " + testDao.getUserStatus(userId));

                //getContactsForUser test
                List<Contact> contacts = testDao.getContactsForUser(userId);
                contacts.forEach(contact -> {
                    System.out.println(contact.getFriendshipStatus());
                    System.out.println(contact.getContactDate());
                    System.out.println(contact.getUserId());
                    System.out.println("......................");
                });

                //getNotificationForUser test
//                List<Notification> notifications = testDao.getNotificationForUser(userId);
//                notifications.forEach(notification -> {
//                    System.out.println(notification.getNotificationId());
//                    System.out.println(notification.getToUserId());
//                    System.out.println(notification.getFromUserName());
//                    System.out.println(notification.getType());
//                    System.out.println(notification.getBody());
//                    System.out.println("......................");
//                });

                //getUserPendingRequests(toUser)
//                List<PendingRequest> pendingRequests = testDao.getUserPendingRequests(userId);
//                pendingRequests.forEach(request -> {
//                    System.out.println(request.getToUserId());
//                    System.out.println(request.getFromUserId());
//                    System.out.println(request.getSentDate());
//                    System.out.println(request.getBody());
//                    System.out.println("......................");
//                });
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            toggleSwitch.setDisable(true);
            disableButtons();
            new Thread(() -> {
                performOperation();
                Platform.runLater(() -> {
                    toggleSwitch.setDisable(false);
                    handleToggleSwitchChange(isSwitchOn);
                });
            }).start();
        });
    }

    private void setupButtonHandlers() {
        button1.setOnAction(event -> handleButtonClick(button1, button2, button3));
        button2.setOnAction(
                event -> {
                    handleButtonClick(button2, button1, button3);
                    mainNavigatorPane.setCenter(announcementPane);
        });
        button3.setOnAction(event -> handleButtonClick(button3, button1, button2));
    }

    private void handleButtonClick(Button clickedButton, Button otherButton1, Button otherButton2) {

        clickedButton.setStyle("-fx-background-color: #fe3554; -fx-background-radius: 20;");
        otherButton1.setStyle("-fx-background-color: #8061c5; -fx-background-radius: 20;");
        otherButton2.setStyle("-fx-background-color: #8061c5; -fx-background-radius: 20;");
    }


    private void performOperation() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleToggleSwitchChange(boolean isSwitchOn) {
        if (isSwitchOn) {
            enableButtons();
            setupButtonHandlers();
        } else {
            disableButtons();
        }
    }

    private void enableButtons() {
        button1.setDisable(false);
        button2.setDisable(false);
        button3.setDisable(false);
        cantDoAnyThingLabel.setVisible(false);
        setupButtonHandlers();
    }

    private void disableButtons() {
        button1.setDisable(true);
        button2.setDisable(true);
        button3.setDisable(true);
        cantDoAnyThingLabel.setVisible(true);
    }

}