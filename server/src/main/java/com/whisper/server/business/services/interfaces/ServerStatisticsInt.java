package com.whisper.server.business.services.interfaces;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.List;
import java.util.Map;

public interface ServerStatisticsInt {
    public void drawEntriesChart();
    public void setOnlineUsers();
    public void setOfflineUsers();
    public void drawGenderChart();
    public void drawCountryChart();
    ObservableList<XYChart.Data<String, Number>> convertToObservableList(List<Map<String, Number>> topCountries);
    public void updateChartWithData(ObservableList<XYChart.Data<String, Number>> data);
}
