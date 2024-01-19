package com.whisper.server.datalayer.db;

import com.whisper.server.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyDatabase {
    private static MyDatabase instance = null;
    private Connection connection = null;

    private MyDatabase(){

    }
    public static synchronized MyDatabase getInstance(){
        if(instance == null)
            instance = new MyDatabase();
        return instance;
    }

    public void startConnection() throws SQLException{
        connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s",
                Constants.HOST, Constants.PORT, Constants.DB_NAME), Constants.USERNAME, Constants.PASSWORD);

    }
    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException{
        if (isConnectionValidAndNotNull()){
            connection.close();
            System.out.println("Connection closed");
        }
    }


    public void commit() throws SQLException{
        if(isConnectionValidAndNotNull())
            connection.commit();
    }

    private boolean isConnectionValidAndNotNull() throws SQLException{
        return connection!=null && connection.isValid(0);
    }
}
