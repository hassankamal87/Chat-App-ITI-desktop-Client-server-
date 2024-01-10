package com.whisper.server.homeserver;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private ToggleButton toggleSwitch;

    @FXML
    public void initialize() {

        disableButtons();
        setupToggleSwitchHandler();
        toggleSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> handleToggleSwitchChange(newValue));
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

    private void setupToggleSwitchHandler() {
        toggleSwitch.setOnAction(event -> {
            toggleSwitch.setDisable(true);
            disableButtons();
            new Thread(() -> {
                performOperation();
                Platform.runLater(() -> {
                    toggleSwitch.setDisable(false);
                    handleToggleSwitchChange(toggleSwitch.isSelected());
                });
            }).start();
        });
    }

    private void performOperation() {
        try {
            Thread.sleep(5000);
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