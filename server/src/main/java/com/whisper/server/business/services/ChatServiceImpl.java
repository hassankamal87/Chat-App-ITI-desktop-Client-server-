package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.MessageDao;
import com.whisper.server.persistence.daos.RoomChatDao;
import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.daos.interfaces.MessageDaoInterface;
import com.whisper.server.persistence.daos.interfaces.RoomChatDaoInterface;
import com.whisper.server.persistence.daos.interfaces.UserDaoInterface;
import com.whisper.server.persistence.db.MyDatabase;
import org.example.clientinterfaces.ClientInterface;
import org.example.entities.*;
import org.example.serverinterfaces.ChatServiceInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatServiceInt {

    private HashMap<Integer,ClientInterface> clients = new HashMap<>();

    private static ChatServiceImpl instance = null;
    protected ChatServiceImpl() throws RemoteException {
    }
    public static synchronized ChatServiceInt getInstance() throws RemoteException{
        if(instance == null){
            instance = new ChatServiceImpl();
        }
        return instance;
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        MessageDaoInterface dao = MessageDao.getInstance(MyDatabase.getInstance());
        try {
            dao.createMessage(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        broadCastMessageToCustomChat(message);
    }

    private void broadCastMessageToCustomChat(Message message) {
        RoomChatDaoInterface roomChatDao = RoomChatDao.getInstance(MyDatabase.getInstance());
        List<User> users;
        try {
            users = roomChatDao.getAllUsers(message.getToChatId());
            users.forEach(user -> System.out.println(user.getUserId()));
            List<Integer> userIds = users.stream()
                    .map(User::getUserId)
                    .toList();
            clients.forEach((id, client) -> {
                if (message.getFromUserId() != id && userIds.contains(id)){
                    try {
                        client.notifyUserWithMessage(message);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerUser(int userId, ClientInterface client) throws RemoteException {
        clients.put(userId,client);
    }

    @Override
    public void unRegisterUser(int userId, ClientInterface client) throws RemoteException {
        clients.remove(userId,client);
    }

    @Override
    public List<RoomChat> getRoomChatsForUser(int userId) throws RemoteException {
        try {
            RoomChatDaoInterface roomChatDao = RoomChatDao.getInstance(MyDatabase.getInstance());
            return roomChatDao.getAllRoomChats(userId);
        } catch (SQLException e) {
            System.out.println("chatServiceImpl line 82");
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public int getRoomChatForUsers(int user1, int user2) throws RemoteException {
        RoomChatDaoInterface roomChatDao = RoomChatDao.getInstance(MyDatabase.getInstance());
        try {
            return roomChatDao.getRoomChatForUsers(user1,user2);
        } catch (SQLException e) {
            System.out.println("Sql execption get room chat id");
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createRoomChat(int user1Id, int user2Id) throws RemoteException{
        RoomChatDaoInterface roomChatDao = RoomChatDao.getInstance(MyDatabase.getInstance());
        try {
            int newRoomChatID =  roomChatDao.createRoomChat(new RoomChat(-1,Date.valueOf(LocalDate.now()),true,"",null,user1Id,"",Type.individual));
            roomChatDao.addRoomMember(new RoomMember(newRoomChatID, user1Id));
            roomChatDao.addRoomMember(new RoomMember(newRoomChatID, user2Id));
            return newRoomChatID;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createGroupChat(int user1Id, List<User> users) throws RemoteException{
        RoomChatDaoInterface roomChatDao = RoomChatDao.getInstance(MyDatabase.getInstance());
        UserDaoInterface userDao = UserDao.getInstance(MyDatabase.getInstance());
        try {
            User adminUser = userDao.getUserById(user1Id);
            StringBuilder groupChatName = new StringBuilder();
            groupChatName.append(adminUser.getUserName(), 0, 5).append(", ");
            for(User user : users){
                groupChatName.append(user.getUserName(), 0, 5).append(", ");
            }
            int newRoomChatID =  roomChatDao.createRoomChat(new RoomChat(-1,Date.valueOf(LocalDate.now()),true,groupChatName.toString(),null,user1Id,"",Type.group));
            roomChatDao.addRoomMember(new RoomMember(newRoomChatID,user1Id));
            for(User user: users){
                roomChatDao.addRoomMember(new RoomMember(newRoomChatID,user.getUserId()));
            }
            return newRoomChatID;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<User> getUsersForRoomChat(int roomChatId) throws RemoteException {
        RoomChatDaoInterface roomChatDao = RoomChatDao.getInstance(MyDatabase.getInstance());
        try {
            return roomChatDao.getAllUsers(roomChatId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> getAllMessagesForRoomChat(int roomChatId) throws RemoteException {
        MessageDaoInterface messageDao = MessageDao.getInstance(MyDatabase.getInstance());
        try {
            return messageDao.getAllByChatId(roomChatId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RoomChat getRoomChatByID(int roomChatId) throws RemoteException {
        RoomChatDaoInterface roomChatDao = RoomChatDao.getInstance(MyDatabase.getInstance());
        try {
            return roomChatDao.getRoomChat(roomChatId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
