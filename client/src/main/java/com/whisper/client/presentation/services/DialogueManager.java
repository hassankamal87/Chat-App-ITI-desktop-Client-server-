package com.whisper.client.presentation.services;

import javafx.scene.control.Alert;

public class DialogueManager {
    private static final DialogueManager instance = new DialogueManager();

    public static DialogueManager getInstance() {
        return instance;
    }

    private DialogueManager() {
    }

    public void showErrorDialog(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void showInformationDialog(String title, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void showWarningDialog(String title, String contentText){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void showConfirmationDialog(String title, String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
