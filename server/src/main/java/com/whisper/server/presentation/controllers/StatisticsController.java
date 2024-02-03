package com.whisper.server.presentation.controllers;

import com.whisper.server.business.services.ServerStatistics;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.Collection;
import java.util.Map;

public class StatisticsController implements StatisticsUpdateInt {
    @FXML
    private PieChart genderChart;
    @FXML
    private Label onlineUsers;
    @FXML
    private Label offlineUsers;
    @FXML
    private BarChart<String, Integer> countryChart;
    @FXML
    private LineChart<String, Integer> entryChart;
    private IntegerProperty onlineCount = new SimpleIntegerProperty(0);
    private IntegerProperty offlineCount = new SimpleIntegerProperty(0);
    private ObservableList<XYChart.Series<String, Integer>> countryChartData = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> genderChartData = FXCollections.observableArrayList();
    //private Map<String,Integer> entryData = null;
    private ServerStatistics serverStatistics;
    @FXML
    public void initialize() {
        serverStatistics = ServerStatistics.getInstance();
        serverStatistics.setStatisticsUpdateInt(this);

        setDate();

    }

    private void setDate() {
        onlineUsers.textProperty().bind(onlineCount.asString());
        offlineUsers.textProperty().bind(offlineCount.asString());
        genderChart.setData(genderChartData);
        countryChart.setData(countryChartData);
    }

    @Override
    public void updateOnlineOfflineUsers(int onlineCount, int offlineCount) {
        this.onlineCount.set(onlineCount);
        this.offlineCount.set(offlineCount);
    }

    @Override
    public void updateGenderChart(int maleCount, int femaleCount) {
        genderChartData.setAll(
                new PieChart.Data("Male", maleCount),
                new PieChart.Data("Female", femaleCount)
        );
    }

    @Override
    public void updateCountryChart(Map<String, Integer> countryData) {
        ObservableList<XYChart.Series<String, Integer>> updatedData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : countryData.entrySet()) {
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(entry.getKey());
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            updatedData.add(series);
        }
        countryChartData.setAll(updatedData);
    }



    // ---------------------------------------------------------



//    private void setOnlineOfflineUsers() {
//        updateOnlineOfflineUsers();
//    }
//    @Override
//    public void updateOnlineOfflineUsers(int onlineCount, int offlineCount) {
//        this.onlineCount.set(onlineCount);
//        this.offlineCount.set(offlineCount);
//    }
//    private void updateOnlineOfflineUsers() {
//        onlineCount.set(ServerStatistics.getInstance().getOnlineUsers());
//        offlineCount.set(ServerStatistics.getInstance().getOfflineUsers());
//    }
//
//    private void setGenderChart(){
//        genderChart.setData(updateGenderChart());
//    }
//
//    private ObservableList<PieChart.Data> updateGenderChart(){
//        int male = ServerStatistics.getInstance().getMaleUsers();
//        int female = ServerStatistics.getInstance().getFemaleUsers();
//
//        genderChartData.setAll(
//                new PieChart.Data("Male",male),
//                new PieChart.Data("Female",female)
//        );
//
//        return genderChartData;
//    }
//
//    private void setCountryChart(){
//        countryChart.getData().setAll(updateCountryChart());
//    }
//
//    private XYChart.Series<String, Integer> updateCountryChart(){
//        Map<String, Integer> countryData = ServerStatistics.getInstance().getTopCountries();
//        XYChart.Series<String, Integer> series = new XYChart.Series<>();
//
//        for (Map.Entry<String, Integer> entry : countryData.entrySet()) {
//            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
//        }
//
//        return series;
//    }



//    private void setEntryChart(){
//         entryData = ServerStatistics.getInstance().getEntryData();
//        XYChart.Series<String, Integer> series = new XYChart.Series<>();
//        for (Map.Entry<String, Integer> entry : entryData.entrySet()) {
//            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
//        }
//
//        entryChart.getData().setAll(series);
//    }
}
