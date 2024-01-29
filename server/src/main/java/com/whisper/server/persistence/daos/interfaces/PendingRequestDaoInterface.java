package com.whisper.server.persistence.daos.interfaces;

import org.example.entities.PendingRequest;

import java.sql.SQLException;
import java.util.List;

public interface PendingRequestDaoInterface {
    //create PendingRequest
    //Read PendingRequest
    //delete PendingRequest
    int createPendingRequest (PendingRequest request) throws SQLException;

    List<PendingRequest> getAllPendingRequest(int toUserId) throws SQLException;

    PendingRequest getPendingRequest(int toUserId, int fromUserId) throws SQLException;

    int deletePendingRequest(int toUserId) throws SQLException;

}
