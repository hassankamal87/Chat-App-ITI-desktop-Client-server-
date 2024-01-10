package com.whisper.server.homeserver.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class homeServerController {

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

    public void initialize() {
        disableButtons();
        setupToggleSwitch();
        setupToggleSwitchHandler();
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
        button2.setOnAction(event -> handleButtonClick(button2, button1, button3));
        button3.setOnAction(event -> handleButtonClick(button3, button1, button2));
    }

    private void handleButtonClick(Button clickedButton, Button otherButton1, Button otherButton2) {
        clickedButton.setStyle("-fx-background-color: #fe3554; -fx-background-radius: 20;");
        otherButton1.setStyle("-fx-background-color: #8061c5; -fx-background-radius: 20;");
        otherButton2.setStyle("-fx-background-color: #8061c5; -fx-background-radius: 20;");
    }


    private void performOperation() {
        try {
            Thread.sleep(3000);
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