package com.whisper.client.presentation.services;

import javafx.scene.control.Alert;

public class ErrorDialogue {
    public Alert setData(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
        return alert;
    }
}
