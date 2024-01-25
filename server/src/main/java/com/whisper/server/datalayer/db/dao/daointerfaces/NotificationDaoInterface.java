package com.whisper.server.datalayer.db.dao.daointerfaces;

import com.whisper.server.model.Notification;

import java.sql.SQLException;
import java.util.List;

public interface NotificationDaoInterface {
    int create (Notification object) throws SQLException;
    List<Notification> getById (int id) throws SQLException;

    int deleteById (int id) throws SQLException;

}
