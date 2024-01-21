package com.whisper.server.datalayer.db.dao.daointerfaces;

import com.whisper.server.model.PendingRequest;

import java.sql.SQLException;
import java.util.List;

public interface PendingRequestDaoInterface {
    //create PendingRequest
    //Read PendingRequest
    //delete PendingRequest
    int create (PendingRequest request) throws SQLException;

    List<PendingRequest> getAllPendingRequest(int toUserId) throws SQLException;

    PendingRequest getPendingRequest(int toUserId, int fromUserId) throws SQLException;

    int deletePendingRequest(int toUserId) throws SQLException;

}
