package com.whisper.server.presentation.controllers;

//import com.whisper.server.business.services.SendContactsInvitationServiceImpl;
import com.whisper.server.business.services.SendContactsInvitationServiceImpl;
import com.whisper.server.business.services.ServerService;
import com.whisper.server.persistence.db.MyDatabase;
import com.whisper.server.presentation.services.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
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
import org.example.clientinterfaces.ClientServiceInt;
import org.example.serverinterfaces.SendContactsInvitationServiceInt;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
    public static volatile boolean isSwitchOn = false;
    private Rectangle rectangle;
    private Circle circle;
    @FXML
    private BorderPane mainNavigatorPane;
    private Parent announcementPane = null;
    private Parent welcomePane = null;
    private Parent usersPane = null;
    private final ServerService serverService = ServerService.getInstance();
    private final MyDatabase myDatabase = MyDatabase.getInstance();
    private Parent statisticsPane = null;
    private void getStatisticsPane(){
        statisticsPane = SceneManager.getInstance().loadPane("statisticsView");
    }
    private void closeStatisticsPane(){
        statisticsPane = null;
    }
    @FXML
    public void initialize() {
        gettingPanes();
        disableButtons();
        setupToggleSwitch();
        setupToggleSwitchHandler();
    }
    private void gettingPanes(){
        announcementPane = SceneManager.getInstance().loadPane("announcerHomeView");
        welcomePane = SceneManager.getInstance().loadPane("welcomeView");
        //statisticsPane = SceneManager.getInstance().loadPane("statisticsView");
        usersPane = SceneManager.getInstance().loadPane("users-view");
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
                performOperation();
                isSwitchOn = false;
            } else {
                translateTransition.setToX(-15);
                rectangle.setFill(Color.GREEN);
                isSwitchOn = true;
            }
            translateTransition.play();
            //handleToggleSwitchChange(isSwitchOn);

            toggleSwitch.setDisable(true);
            disableButtons();
            new Thread(() -> {

                Platform.runLater(() -> {
                    toggleSwitch.setDisable(false);
                    handleToggleSwitchChange(isSwitchOn);
                });
            }).start();
        });
    }

    private void setupButtonHandlers() {
        button1.setOnAction(
                event -> {
                    handleButtonClick(button1, button2, button3);
                    mainNavigatorPane.setCenter(usersPane);
                });
        button2.setOnAction(
                event -> {
                    handleButtonClick(button2, button1, button3);
                    mainNavigatorPane.setCenter(announcementPane);
                });
        button3.setOnAction(
                event -> {
                    handleButtonClick(button3, button1, button2);
                    mainNavigatorPane.setCenter(statisticsPane);
                });
    }

    private void handleButtonClick(Button clickedButton, Button otherButton1, Button otherButton2) {

        clickedButton.setStyle("-fx-background-color: #597E52; -fx-background-radius: 25;");
        otherButton1.setStyle("-fx-background-color: #C6A969; -fx-background-radius: 15;");
        otherButton2.setStyle("-fx-background-color: #C6A969; -fx-background-radius: 15;");
    }


    private void performOperation()  {

        try {
            SendContactsInvitationServiceInt sendContactsInvitationService= SendContactsInvitationServiceImpl.getInstance();

            CopyOnWriteArrayList<ClientServiceInt> clients = SendContactsInvitationServiceImpl.clientsVector;
            for(ClientServiceInt c:clients){
                sendContactsInvitationService.ServerUnRegister(c);
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    private void handleToggleSwitchChange(boolean isSwitchOn) {
        if (isSwitchOn) {
            ServerService.getInstance().startServer();
            enableButtons();
            setupButtonHandlers();
            getStatisticsPane();
        } else {
            ServerService.getInstance().stopServer();
            disableButtons();
            mainNavigatorPane.setCenter(welcomePane);
            button1.setStyle("-fx-background-color: #C6A969; -fx-background-radius: 15;");
            button2.setStyle("-fx-background-color: #C6A969; -fx-background-radius: 15;");
            button3.setStyle("-fx-background-color: #C6A969; -fx-background-radius: 15;");
            closeStatisticsPane();
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
