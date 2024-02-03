package com.whisper.server.presentation.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Map;

public interface StatisticsUpdateInt {
    void updateOnlineOfflineUsers(SimpleIntegerProperty onlineCount, IntegerProperty offlineCount);

    void updateGenderChart(int maleCount, int femaleCount);
    void updateCountryChart(Map<String,Integer> countryData);

    //void updateGenderChart();
    //void updateCountryChart();
}
