package com.whisper.server.persistence.daos;

import com.whisper.server.persistence.db.MyDatabase;
import com.whisper.server.persistence.daos.interfaces.PendingRequestDaoInterface;
import org.example.entities.PendingRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PendingRequestDao implements PendingRequestDaoInterface {
    private MyDatabase myDatabase = null;
    private static PendingRequestDao instance = null;

    private PendingRequestDao(MyDatabase myDatabase) {
        this.myDatabase = myDatabase;
    }
    public synchronized static PendingRequestDao getInstance(MyDatabase myDatabase) {
        if (instance == null) {
            instance = new PendingRequestDao(myDatabase);
        }
        return instance;
    }

    @Override
    public int createPendingRequest(PendingRequest request) throws SQLException {
        String query = "Insert into pending_request (to_user_id, from_user_id, sent_date, body) " +
                "values (?, ?, ?, ?)";
        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)){
            ps.setInt(1, request.getToUserId());
            ps.setInt(2, request.getFromUserId());
            ps.setString(3, request.getSentDate());
            ps.setString(4, request.getBody());
            return ps.executeUpdate();
        }
    }

    @Override
    public List<PendingRequest> getAllPendingRequest(int toUserId) throws SQLException {
        List<PendingRequest> requests = new ArrayList<>();
        String query = "Select * from pending_request where to_user_id = ?";
        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)){
            ps.setInt(1, toUserId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                PendingRequest request = new PendingRequest(
                        rs.getInt("to_user_id"),
                        rs.getInt("from_user_id"),
                        rs.getString("sent_date"),
                        rs.getString("body")
                );
                requests.add(request);
            }
            rs.close();
        }
        return requests;
    }

    @Override
    public PendingRequest getPendingRequest(int toUserId, int fromUserId) throws SQLException {
        PendingRequest request = null ;
        String query = "Select * from pending_request where to_user_id = ? and from_user_id = ?";
        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setInt(1, toUserId);
            ps.setInt(2, fromUserId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                request = new PendingRequest(
                        rs.getInt("to_user_id"),
                        rs.getInt("from_user_id"),
                        rs.getString("sent_date"),
                        rs.getString("body")
                );
            }
            rs.close();
            return request;
        }
    }

    @Override
    public int deletePendingRequest(int toUserId ,int fromUserId) throws SQLException {
        String query = "Delete from pending_request where to_user_id = ? and from_user_id = ?";
        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)){
            ps.setInt(1, toUserId);
            ps.setInt(2, fromUserId);
            return ps.executeUpdate();
        }
    }

}
