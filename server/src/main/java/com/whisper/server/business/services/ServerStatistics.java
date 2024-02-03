package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.db.MyDatabase;
import com.whisper.server.presentation.controllers.StatisticsUpdateInt;
import javafx.application.Platform;
import javafx.scene.chart.PieChart;

import java.sql.SQLException;
import java.util.Map;

public class ServerStatistics {
    private StatisticsUpdateInt statisticsUpdateInt;
    private static ServerStatistics instance = null;
    private ServerStatistics() {
    }
    public synchronized static ServerStatistics getInstance() {
        if (instance == null) {
            instance = new ServerStatistics();
        }
        return instance;
    }
    public void setStatisticsUpdateInt(StatisticsUpdateInt statisticsUpdateInt) {
        this.statisticsUpdateInt = statisticsUpdateInt;
        updateData();
    }

    public void updateData(){
        updateOnlineOfflineUsers();
        updateGenderChart();
        updateCountryChart();
    }

    private void updateOnlineOfflineUsers() {
        try {
            int onlineUsers = UserDao.getInstance(MyDatabase.getInstance()).getOnlineUsersCount();
            int offlineUsers = UserDao.getInstance(MyDatabase.getInstance()).getOfflineUsersCount();



            statisticsUpdateInt.updateOnlineOfflineUsers(onlineUsers, offlineUsers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateGenderChart() {
        try {
            int maleUsers = UserDao.getInstance(MyDatabase.getInstance()).getMaleUsersCount();
            int femaleUsers = UserDao.getInstance(MyDatabase.getInstance()).getFemaleUsersCount();

            statisticsUpdateInt.updateGenderChart(maleUsers, femaleUsers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateCountryChart() {
        try {
            Map<String, Integer> countryData = UserDao.getInstance(MyDatabase.getInstance()).getTopCountries();
            statisticsUpdateInt.updateCountryChart(countryData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //---------------------------------------------------

//    public  getOnlineOfflineUsers() {
//        try {
//            int onlineUsers = UserDao.getInstance(MyDatabase.getInstance()).getOnlineUsersCount();
//            int offlineUsers = UserDao.getInstance(MyDatabase.getInstance()).getOfflineUsersCount();
//
//            statisticsUpdateInt.updateOnlineOfflineUsers(onlineUsers, offlineUsers);
//
//            return onlineUsers;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//
//    public int getMaleUsers() {
//        try {
//            return UserDao.getInstance(MyDatabase.getInstance()).getMaleUsersCount();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public int getFemaleUsers() {
//        try {
//            return UserDao.getInstance(MyDatabase.getInstance()).getFemaleUsersCount();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Map<String, Integer> getTopCountries() {
//        try {
//            return UserDao.getInstance(MyDatabase.getInstance()).getTopCountries();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public Map<String, Integer> getEntryData() {
//
//    }
}
