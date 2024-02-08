package com.whisper.server.business.services;

import com.whisper.server.persistence.daos.MessageDao;
import com.whisper.server.persistence.daos.RoomChatDao;
import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.daos.interfaces.MessageDaoInterface;
import com.whisper.server.persistence.daos.interfaces.RoomChatDaoInterface;
import com.whisper.server.persistence.daos.interfaces.UserDaoInterface;
import com.whisper.server.persistence.db.MyDatabase;
import com.whisper.server.utils.Constants;
import org.example.clientinterfaces.ClientInterface;
import org.example.entities.*;
import org.example.serverinterfaces.ChatServiceInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatServiceInt {

    private HashMap<Integer, ClientInterface> clients = new HashMap<>();

    private static ChatServiceImpl instance = null;

    protected ChatServiceImpl() throws RemoteException {
    }

    public static synchronized ChatServiceInt getInstance() throws RemoteException {
        if (instance == null) {
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

    @Override
    public void sendFileMessage(int senderId, int roomChatId, File file) throws RemoteException {
        MessageDaoInterface dao = MessageDao.getInstance(MyDatabase.getInstance());
        String filePath = saveFileInSeparateDirector(file);
        Message newMessage = new Message(-1, roomChatId, Date.valueOf(LocalDate.now()), senderId, file.getName(), filePath);
        try {
            dao.createMessage(newMessage);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        broadCastMessageToCustomChat(newMessage);
    }

    private String saveFileInSeparateDirector(File file) {
        try {
            String absolutePath = Constants.FILES_PATH;
            Path targetDirectory = Paths.get(absolutePath);
            Files.createDirectories(targetDirectory);
            Path destinationPath = targetDirectory.resolve(file.getName());
            Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("file saved to directory: " + destinationPath);
            return destinationPath.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                if (message.getFromUserId() != id && userIds.contains(id)) {
                    try {
                        if (message.getAttachment() == null) {
                            client.notifyUserWithMessage(message);
                        }else {
                            File file = readFileFromDirectory(message.getBody());
                            client.notifyUserWithFile(message,file);
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private File readFileFromDirectory(String fileName) {
        try {
            String absolutePath = Constants.FILES_PATH;
            Path filePath = Paths.get(absolutePath, fileName);

            // Check if the file exists before returning
            if (Files.exists(filePath)) {
                return filePath.toFile();
            } else {
                throw new FileNotFoundException("File not found: " + fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void registerUser(int userId, ClientInterface client) throws RemoteException {
        clients.put(userId, client);
    }

    @Override
    public void unRegisterUser(int userId, ClientInterface client) throws RemoteException {
        clients.remove(userId, client);
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
            return roomChatDao.getRoomChatForUsers(user1, user2);
        } catch (SQLException e) {
            System.out.println("Sql execption get room chat id");
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createRoomChat(int user1Id, int user2Id) throws RemoteException {
        RoomChatDaoInterface roomChatDao = RoomChatDao.getInstance(MyDatabase.getInstance());
        try {
            int newRoomChatID = roomChatDao.createRoomChat(new RoomChat(-1, Date.valueOf(LocalDate.now()), true, "", null, user1Id, "", Type.individual));
            roomChatDao.addRoomMember(new RoomMember(newRoomChatID, user1Id));
            roomChatDao.addRoomMember(new RoomMember(newRoomChatID, user2Id));
            return newRoomChatID;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createGroupChat(int user1Id, List<User> users) throws RemoteException {
        RoomChatDaoInterface roomChatDao = RoomChatDao.getInstance(MyDatabase.getInstance());
        UserDaoInterface userDao = UserDao.getInstance(MyDatabase.getInstance());
        try {
            User adminUser = userDao.getUserById(user1Id);
            StringBuilder groupChatName = new StringBuilder();
            groupChatName.append(adminUser.getUserName(), 0, 4).append(", ");
            for (User user : users) {
                groupChatName.append(user.getUserName(), 0, 4).append(", ");
            }
            groupChatName.delete(groupChatName.length() - 2, groupChatName.length());

            int newRoomChatID = roomChatDao.createRoomChat(new RoomChat(-1, Date.valueOf(LocalDate.now()), true, groupChatName.toString(), null, user1Id, "", Type.group));
            roomChatDao.addRoomMember(new RoomMember(newRoomChatID, user1Id));
            for (User user : users) {
                roomChatDao.addRoomMember(new RoomMember(newRoomChatID, user.getUserId()));
            }
            return newRoomChatID;
        } catch (SQLException e) {
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
    public List<File> getAllFilesForRoomChat(int roomChatId) throws RemoteException {
        List<File> files = new ArrayList<>();
        MessageDaoInterface messageDao = MessageDao.getInstance(MyDatabase.getInstance());
        try {
            List<Message> messages =  messageDao.getAllByChatId(roomChatId);
            for (Message message: messages){
                if (message.getAttachment() != null){
                    File file = readFileFromDirectory(message.getBody());
                    files.add(file);
                }
            }
            return files;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashMap<Message, File> getMessagesAndFilesForRoomChat(int roomChatId) throws RemoteException {
        HashMap<Message,File> mesFiles = new HashMap<>();
        MessageDaoInterface messageDao = MessageDao.getInstance(MyDatabase.getInstance());
        try {
            List<Message> messages =  messageDao.getAllByChatId(roomChatId);
            for (Message message: messages){
                if (message.getAttachment() == null){
                    mesFiles.put(message,null);
                }else{
                    File file = readFileFromDirectory(message.getBody());
                    mesFiles.put(message,file);
                }
            }
            return mesFiles;
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

    @Override
    public User getUserById(int userId) throws RemoteException {
        UserDaoInterface userDao = UserDao.getInstance(MyDatabase.getInstance());
        try {
            return userDao.getUserById(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
