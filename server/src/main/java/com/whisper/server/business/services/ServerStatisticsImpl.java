package com.whisper.server.business.services;

import com.whisper.server.business.services.interfaces.ServerStatisticsInt;
import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.db.MyDatabase;
import com.whisper.server.presentation.controllers.HomeServerController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ServerStatisticsImpl implements Runnable , ServerStatisticsInt {
    private PieChart genderChart;
    private Label onlineUsers;
    private Label offlineUsers;
    private BarChart countryChart;
    private LineChart entryChart;
    private static int timer = 0;
    ObservableList<XYChart.Data<String, Number>> countryData = FXCollections.observableArrayList();
    XYChart.Series<String, Number> series = new XYChart.Series<>();


    public ServerStatisticsImpl(PieChart genderChart, Label onlineUsers, Label offlineUsers, BarChart countryChart, LineChart entryChart) {
        this.genderChart = genderChart;
        this.onlineUsers = onlineUsers;
        this.offlineUsers = offlineUsers;
        this.countryChart = countryChart;
        this.entryChart = entryChart;
    }

    @Override
    public void run() {
        while (HomeServerController.isSwitchOn) {
            try {
                //update charts every 2 seconds
                Thread.sleep(2000 );
                if(!HomeServerController.isSwitchOn)
                    break;
                Platform.runLater(() -> {
                    timer += 1;
                    // clear charts every 2 hours
                    if (timer >= 60*60) {
                        entryChart.getData().clear();
                        timer = 0;
                    }
                    try {
                        countryData = convertToObservableList(UserDao.getInstance(MyDatabase.getInstance()).getTopCountries());
                    } catch (SQLException e) {
                        System.out.println("SQL Exception : " + e);
                        throw new RuntimeException(e);
                    }
                    setOnlineUsers();
                    setOfflineUsers();
                    drawGenderChart();
                    updateChartWithData(countryData);
                    drawEntriesChart();
                    System.out.println("update charts");
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void drawEntriesChart() {
        // Get the current time in format hh:mm
        Time time = Time.valueOf(LocalDateTime.now().toLocalTime());

        // Get the count of online users
        int onlineUsersCount;
        try {
            onlineUsersCount = UserDao.getInstance(MyDatabase.getInstance()).getOnlineUsersCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Create a new XYChart.Data object
        XYChart.Data<String, Number> data = new XYChart.Data<>(String.valueOf(time), onlineUsersCount);

        // Create a new XYChart.Series object and add the data to it
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Online Users");
        series.getData().add(data);

        entryChart.getData().add(series);
    }

    @Override
    public void setOnlineUsers() {
        try {
            onlineUsers.setText(UserDao.getInstance(MyDatabase.getInstance()).getOnlineUsersCount() + " Users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setOfflineUsers() {
        try {
            offlineUsers.setText(UserDao.getInstance(MyDatabase.getInstance()).getOfflineUsersCount() + " Users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void drawGenderChart() {
        int maleCount, femaleCount;
        try {
            maleCount = UserDao.getInstance(MyDatabase.getInstance()).getMaleUsersCount();
            femaleCount = UserDao.getInstance(MyDatabase.getInstance()).getFemaleUsersCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<PieChart.Data> genderData = FXCollections.observableArrayList(
                new PieChart.Data("Male", maleCount),
                new PieChart.Data("Female", femaleCount)
        );
        genderChart.setData(genderData);
        for (PieChart.Data data : genderChart.getData()) {
            String percentage = String.format("%.1f%%", (data.getPieValue() / (maleCount + femaleCount)) * 100);
            Tooltip tooltip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), tooltip);
        }
    }

    @Override
    public void drawCountryChart() {
        try {
            List<Map<String, Number>> topCountries = UserDao.getInstance(MyDatabase.getInstance()).getTopCountries();

            countryData = convertToObservableList(topCountries);
            updateChartWithData(countryData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<XYChart.Data<String, Number>> convertToObservableList(List<Map<String, Number>> topCountries) {
        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();

        for (Map<String, Number> country : topCountries) {
            for (Map.Entry<String, Number> entry : country.entrySet()) {
                data.add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
        }
        return data;
    }

    @Override
    public void updateChartWithData(ObservableList<XYChart.Data<String, Number>> data) {
        countryChart.getData().clear();

        series = new XYChart.Series<>();
        series.setName("Entries");

        series.getData().addAll(data);

        countryChart.getData().add(series);
    }
}
