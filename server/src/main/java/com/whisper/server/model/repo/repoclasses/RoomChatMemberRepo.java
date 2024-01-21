package com.whisper.server.model.repo.repoclasses;

import com.whisper.server.datalayer.db.dao.daointerfaces.RoomMemberDaoInterface;
import com.whisper.server.model.RoomChat;
import com.whisper.server.model.RoomMember;
import com.whisper.server.model.User;
import com.whisper.server.model.repo.repointerfaces.RoomMemberRepoInterface;

import java.sql.SQLException;
import java.util.List;

public class RoomChatMemberRepo implements RoomMemberRepoInterface {

    private static RoomMemberRepoInterface instance = null;
    private RoomMemberDaoInterface roomMemberDao = null;

    private RoomChatMemberRepo(RoomMemberDaoInterface dao){
        this.roomMemberDao = dao;
    }
    public static synchronized RoomMemberRepoInterface getInstance(RoomMemberDaoInterface dao){
        if (instance == null)
            instance = new RoomChatMemberRepo(dao);
        return instance;
    }
    @Override
    public int create(RoomMember object) throws SQLException {
        return roomMemberDao.create(object);
    }

    @Override
    public List<RoomChat> getAllRoomChats(int userId) throws SQLException {
        return roomMemberDao.getAllRoomChats(userId);
    }

    @Override
    public List<User> getAllUsers(int roomChatId) throws SQLException {
        return roomMemberDao.getAllUsers(roomChatId);
    }
}
