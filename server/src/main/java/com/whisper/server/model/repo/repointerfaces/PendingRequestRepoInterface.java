package com.whisper.server.model.repo.repointerfaces;

import com.whisper.server.model.PendingRequest;

import java.sql.SQLException;
import java.util.List;

public interface PendingRequestRepoInterface {
    int createPendingRequest (PendingRequest request) throws SQLException;

    List<PendingRequest> getAllPendingRequest(int toUserId) throws SQLException;

    PendingRequest getPendingRequest(int toUserId, int fromUserId) throws SQLException;

    int deletePendingRequest(int toUserId) throws SQLException;
}
