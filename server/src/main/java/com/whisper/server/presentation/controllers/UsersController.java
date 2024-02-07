package com.whisper.server.presentation.controllers;

import com.whisper.server.persistence.daos.UserDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

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
    private UserDao userDao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onInputMethodTextChanged(){

    }
}