package com.whisper.server.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;

public class AnnouncerHomeController
{

    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private AnchorPane announcmentPane;

    public void initialize()
    {

    }

    public void onAnnounceClicked(ActionEvent actionEvent) {
        String htmlText = htmlEditor.getHtmlText();
        htmlEditor.setHtmlText("");

        // the htmlText contains the text with its style
        // need to pass this string to a web view to display it
        // look at the RoomChatController class for an example
        // at method appendMessageInList

    }
}