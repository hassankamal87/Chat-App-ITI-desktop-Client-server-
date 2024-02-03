package com.whisper.server.presentation.controllers;

import com.whisper.server.business.services.ServerStatisticsImpl;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {
    @javafx.fxml.FXML
    private PieChart genderChart=null;
    @javafx.fxml.FXML
    private Label onlineUsers=null;
    @javafx.fxml.FXML
    private Label offlineUsers=null;
    @javafx.fxml.FXML
    private BarChart countryChart=null;
    @javafx.fxml.FXML
    private LineChart entryChart=null;
    private ServerStatisticsImpl serverStatistics=null;
    Thread statThread=null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("StatisticsController");
        serverStatistics = new ServerStatisticsImpl(genderChart, onlineUsers, offlineUsers, countryChart, entryChart);
        statThread = new Thread(serverStatistics);
        statThread.start();
    }
}
