package com.whisper.server.presentation.controllers;

import com.whisper.server.business.services.ClientsProfileService;
import com.whisper.server.business.services.interfaces.UsersServiceInt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.entities.User;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UsersController implements Initializable, UsersServiceInt {

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
    ClientsProfileService clientsProfile;
    ObservableList<User> observableList;
    private ClientsProfileService clientsProfileService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientsProfile = ClientsProfileService.getInstance();
        observableList = FXCollections.observableArrayList();
        clientsProfile = ClientsProfileService.getInstance();
        try {
            clientsProfile.setUsersServiceInt(this);
            observableList.setAll(clientsProfile.getAllUsers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        populateTabelWithData();
    }

    private void populateTabelWithData() {
        try {
            userNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<User, String>("phoneNumber"));
            genderColumn.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
            dobColumn.setCellValueFactory(new PropertyValueFactory<User, Date>("dateOfBirth"));
            countryColumn.setCellValueFactory(new PropertyValueFactory<User, String>("country"));
            modeColumn.setCellValueFactory(new PropertyValueFactory<User, String>("mode"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<User, String>("status"));

            usersTable.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onInputMethodTextChanged(){

    }

    @Override
    public void updateDataFromDb(List<User> updatedUsers) {
        observableList.clear();
        observableList.addAll(updatedUsers);
    }
}