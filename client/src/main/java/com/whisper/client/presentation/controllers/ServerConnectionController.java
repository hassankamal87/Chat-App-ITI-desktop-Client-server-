package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.IPConfig;
import com.whisper.client.presentation.services.DialogueManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.serverinterfaces.AuthenticationServiceInt;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerConnectionController
{
    @FXML
    private TextField ipTextFiled;
    @FXML
    private Button startBtn;

    @FXML
    public void initialize() {
    }

    @FXML
    public void onStartBtnClick(ActionEvent actionEvent) {
        String ip = ipTextFiled.getText();
        if(!ip.isEmpty()){
            try {
                Registry reg = LocateRegistry.getRegistry(ip,8000);
                AuthenticationServiceInt authService = (AuthenticationServiceInt) reg.lookup("authService");
                Stage primaryStage = (Stage) startBtn.getScene().getWindow();
                IPConfig.serverIP = ip;
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/signInView.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                primaryStage.setScene(scene);
            } catch (IOException | NotBoundException e) {
                DialogueManager.getInstance().showWarningDialog("ServerConnection","sorry, this IP does not have a server");
            }
        }
    }
}