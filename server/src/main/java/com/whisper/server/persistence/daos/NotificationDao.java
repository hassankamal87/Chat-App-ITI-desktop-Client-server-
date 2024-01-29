package com.whisper.server.persistence.daos;

import com.whisper.server.persistence.db.MyDatabase;
import com.whisper.server.persistence.daos.interfaces.NotificationDaoInterface;
import org.example.entities.Notification;
import org.example.entities.NotifactionType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDao implements NotificationDaoInterface {
    private MyDatabase myDatabase = null;
    private static NotificationDaoInterface instance = null;

    private NotificationDao(MyDatabase myDatabase) {
        this.myDatabase = myDatabase;
    }
    public synchronized static NotificationDaoInterface getInstance(MyDatabase myDatabase) {
        if (instance == null) {
            instance = new NotificationDao(myDatabase);
        }
        return instance;
    }
    @Override
    public int create(Notification object) throws SQLException {
      String query = "INSERT INTO `db_chat_app`.`notification` " +
                "(`to_user_id`, `from_user_name`, `type`, `body`)" +
                " VALUES (?, ?, ?, ?)";


        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, object.getToUserId());
        ps.setString(2, object.getFromUserName());
        ps.setString(3,"msg");
        ps.setString(4,object.getBody());
        int rowsInserted = ps.executeUpdate();

        ps.close();

        return rowsInserted ;
    }

    @Override
    public List<Notification> getById(int to_user_id) throws SQLException {

        String query ="select * from notification where to_user_id = ?";


        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, to_user_id);



        ResultSet rs = ps.executeQuery();
        List<Notification> notifications = new ArrayList<>();
        while(rs.next()) {
            int id = rs.getInt("notification_id");

            String from_userName = rs.getString("from_user_name");
            String typeString = rs.getString("type");
            NotifactionType notifactionType = switch (typeString) {
                case "msg" -> NotifactionType.msg;
                case "inv" -> NotifactionType.inv;
                default -> NotifactionType.board;
            };
            String body = rs.getString("body");
            Notification notification = new Notification(id, to_user_id, from_userName,notifactionType,body);

            notifications.add(notification);

        }
        ps.close();

        return notifications;
    }



    @Override
    public int deleteById(int id) throws SQLException {
        String query ="delete FROM notification " +
                "WHERE notification_id = ? ";

        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, id);

        int rowsInserted = ps.executeUpdate();
        ps.close();
        return rowsInserted;
    }


}
