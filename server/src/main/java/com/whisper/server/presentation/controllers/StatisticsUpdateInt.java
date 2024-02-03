package com.whisper.server.presentation.controllers;

import java.util.Map;

public interface StatisticsUpdateInt {
    void updateOnlineOfflineUsers(int onlineCount, int offlineCount);

    void updateGenderChart(int maleCount, int femaleCount);
    void updateCountryChart(Map<String,Integer> countryData);

    //void updateGenderChart();
    //void updateCountryChart();
}
