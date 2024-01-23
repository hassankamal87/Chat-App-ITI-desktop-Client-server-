package com.whisper.server.model.repo.repoclasses;

import com.whisper.server.datalayer.db.dao.daointerfaces.PendingRequestDaoInterface;
import com.whisper.server.model.PendingRequest;
import com.whisper.server.model.repo.repointerfaces.PendingRequestRepoInterface;
import java.sql.SQLException;
import java.util.List;

public class PendingRequestRepo implements PendingRequestRepoInterface {

    private static PendingRequestRepoInterface instance = null;
    private PendingRequestDaoInterface requestDao = null;

    private PendingRequestRepo(PendingRequestDaoInterface dao){
        this.requestDao = dao;
    }

    public static synchronized PendingRequestRepoInterface getInstance(PendingRequestDaoInterface dao){
        if (instance == null)
            instance = new PendingRequestRepo(dao);
        return instance;
    }

    @Override
    public int createPendingRequest(PendingRequest request) throws SQLException {
        return requestDao.createPendingRequest(request);
    }

    @Override
    public List<PendingRequest> getAllPendingRequest(int toUserId) throws SQLException {
        return requestDao.getAllPendingRequest(toUserId);
    }

    @Override
    public PendingRequest getPendingRequest(int toUserId, int fromUserId) throws SQLException {
        return requestDao.getPendingRequest(toUserId, fromUserId);
    }

    @Override
    public int deletePendingRequest(int toUserId) throws SQLException {
        return requestDao.deletePendingRequest(toUserId);
    }
}
