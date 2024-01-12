package com.whisper.server.services.db;

import com.whisper.server.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyDatabase {
    private static MyDatabase instance = null;
    private Connection connection = null;


    private MyDatabase() throws SQLException {
        connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s",
                Constants.HOST, Constants.PORT, Constants.DB_NAME), Constants.USERNAME, Constants.PASSWORD);
    }
    public static synchronized MyDatabase getInstance() throws SQLException{
        if(instance == null)
            instance = new MyDatabase();
        return instance;
    }
    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException{
        if (isConnectionValidAndNotNull())
            connection.close();
    }


    public void commit() throws SQLException{
        if(isConnectionValidAndNotNull())
            connection.commit();
    }

    private boolean isConnectionValidAndNotNull() throws SQLException{
        return connection!=null && connection.isValid(0);
    }
}
