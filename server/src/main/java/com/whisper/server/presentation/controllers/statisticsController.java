package com.whisper.server.presentation.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private BarChart entryChart;
    @javafx.fxml.FXML
    private BarChart countryChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawGenderChart();
        drawCountryChart();
//        drawEntriesChart();
    }
    private void drawGenderChart(){
        ObservableList<PieChart.Data> genderData = FXCollections.observableArrayList(
                new PieChart.Data("Male", 15),
                new PieChart.Data("Female", 30)
        );
        genderChart.setData(genderData);
    }

    private void drawCountryChart(){
        ObservableList<XYChart.Data<String, Number>> countryData = FXCollections.observableArrayList(
                new XYChart.Data<>("Egypt", 20),
                new XYChart.Data<>("USA", 35),
                new XYChart.Data<>("Lebanon", 15)
                //dummy data
        );

        countryChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Entries");

        series.getData().addAll(countryData);

        countryChart.getData().add(series);
    }
}
