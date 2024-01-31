package com.whisper.server.presentation.controllers;

import com.whisper.server.business.services.ServerStatisticsImpl;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class statisticsController implements Initializable {
    @javafx.fxml.FXML
    private PieChart genderChart;
    @javafx.fxml.FXML
    private Label onlineUsers;
    @javafx.fxml.FXML
    private Label offlineUsers;
    @javafx.fxml.FXML
    private BarChart countryChart;
    @javafx.fxml.FXML
    private LineChart entryChart;
    private ServerStatisticsImpl serverStatistics;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serverStatistics = new ServerStatisticsImpl(genderChart, onlineUsers, offlineUsers, countryChart, entryChart);
        Thread statThread = new Thread(serverStatistics);
        statThread.start();
    }
}
