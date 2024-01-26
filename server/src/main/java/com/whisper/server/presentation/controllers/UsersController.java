package com.whisper.server.presentation.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class UsersController {

    @FXML
    private TableView usersTable;
    @FXML
    private TableColumn userNameColumn;
    @FXML
    private TableColumn emailColumn;
    @FXML
    private TableColumn phoneColumn;
    @FXML
    private TableColumn genderColumn;
    @FXML
    private TableColumn dobColumn;
    @FXML
    private TableColumn countryColumn;
    @FXML
    private TableColumn modeColumn;
    @FXML
    private TableColumn statusColumn;

    @FXML
    public void initialize() {
    }

    @FXML
    public void onInputMethodTextChanged(Event event) {
    }
}