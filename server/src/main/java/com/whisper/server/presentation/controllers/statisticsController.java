package com.whisper.server.presentation.controllers;

import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.db.MyDatabase;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class statisticsController extends Thread implements Initializable {
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
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000*60*30);
                Platform.runLater(() -> {
                    setOnlineUsers();
                    setOfflineUsers();
                    drawGenderChart();
                    drawCountryChart();
                    drawEntriesChart();
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void drawEntriesChart() {
        // Get the current hour
        int currentHour = LocalDateTime.now().getHour();

        // Get the count of online users
        int onlineUsersCount;
        try {
            onlineUsersCount = UserDao.getInstance(MyDatabase.getInstance()).getOnlineUsersCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Create a new XYChart.Data object
        XYChart.Data<String, Number> data = new XYChart.Data<>(String.valueOf(currentHour), onlineUsersCount);

        // Create a new XYChart.Series object and add the data to it
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Online Users");
        series.getData().add(data);

        // Clear the existing data in the entryChart and add the new series
            entryChart.getData().add(series);

    }

    public void setOnlineUsers() {
        try {
            onlineUsers.setText(UserDao.getInstance(MyDatabase.getInstance()).getOnlineUsersCount()+" Users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOfflineUsers() {
        try {
            offlineUsers.setText(UserDao.getInstance(MyDatabase.getInstance()).getOfflineUsersCount()+" Users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println(UserDao.getInstance(MyDatabase.getInstance()).getOnlineUsersCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setOnlineUsers();
        setOfflineUsers();
        drawGenderChart();
        drawCountryChart();
        start();
        //drawEntriesChart();
    }

    private void drawGenderChart(){
                int maleCount,femaleCount;
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
            String percentage = String.format("%.1f%%", (data.getPieValue() / (maleCount+femaleCount)) * 100);
            Tooltip tooltip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), tooltip);
        }
    }

    private void drawCountryChart(){
        List<Map<String,Number>> topCountries = null;
        try {
            topCountries = UserDao.getInstance(MyDatabase.getInstance()).getTopCountries();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<XYChart.Data<String, Number>> countryData = FXCollections.observableArrayList();
        for (Map<String, Number> country : topCountries) {
            for (Map.Entry<String, Number> entry : country.entrySet()) {
                countryData.add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
        }
        countryChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Entries");

        for (XYChart.Data<String, Number> data : countryData) {
            series.getData().add(data);
        }
        countryChart.getData().add(series);
    }
}
