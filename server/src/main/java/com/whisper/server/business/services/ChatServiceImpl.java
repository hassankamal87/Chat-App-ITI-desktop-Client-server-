package com.whisper.server.business.services;

import com.whisper.server.business.services.interfaces.ChatterBotService;
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
import org.example.utils.Converters;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.Optional;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatServiceInt {

    private HashMap<Integer, ClientInterface> clients = new HashMap<>();

    private static ChatServiceImpl instance = null;

    protected ChatServiceImpl() throws RemoteException {
    }

    public static synchronized ChatServiceImpl getInstance() throws RemoteException {
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
        String filePath = saveFileInSeparateDirector(file, file.getName());
        Message newMessage = new Message(-1, roomChatId, Date.valueOf(LocalDate.now()), senderId, file.getName(), filePath);
        try {
            dao.createMessage(newMessage);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        broadCastMessageToCustomChat(newMessage);
    }

    @Override
    public void sendFileMessage(int senderId, int roomChatId, byte[] fileBytes, String fileName) throws RemoteException {
        MessageDaoInterface dao = MessageDao.getInstance(MyDatabase.getInstance());
        try {
            File file = Converters.convertBytesToFile(fileBytes,fileName);
            String filePath = saveFileInSeparateDirector(file, fileName);
            Message newMessage = new Message(-1, roomChatId, Date.valueOf(LocalDate.now()), senderId, fileName, filePath);
            dao.createMessage(newMessage);
            broadCastMessageToCustomChat(newMessage);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

    private String saveFileInSeparateDirector(File file, String fileName) {
        try {
            String absolutePath = Constants.FILES_PATH;
            Path targetDirectory = Paths.get(absolutePath);
            Files.createDirectories(targetDirectory);
            Path destinationPath = targetDirectory.resolve(fileName);
            Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
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
            List<Integer> userIds = users.stream()
                    .map(User::getUserId)
                    .toList();
            clients.forEach((id, client) -> {
                if (message.getFromUserId() != id && userIds.contains(id)) {
                    try {
                        if (message.getAttachment() == null) {
                            client.notifyUserWithMessage(message);
                        } else {
                            File file = readFileFromDirectory(message);
                            client.notifyUserWithFile(message, Converters.convertFileToBytes(file));
                        }
                    } catch (RemoteException e) {
                        try {
                            SendContactsInvitationServiceImpl.getInstance().ServerUnRegisterWithId(id);
                            unRegisterUser(id, client);
                        } catch (RemoteException ex) {
                            System.out.println("exception in Client Service Impl line 109" + e.getMessage());
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            startMessageBot(users, message);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //check if room chat is individual && if another user is offline and send to him message from Bot
    private void startMessageBot(List<User> users, Message message) {
        if (users.size() == 2) {
            Optional<User> contact = users.stream().filter(user -> user.getUserId() != message.getFromUserId()).findFirst();
            if (contact.get().getStatus() == Status.offline) {

                ClientInterface client = clients.get(message.getFromUserId());
                String botMessage = getMessageFromBot(message.getBody());
                try {
                    client.notifyUserWithMessage(new Message(-3, message.getToChatId(), new java.util.Date(), contact.get().getUserId(), "Bot : " + botMessage, null));
                } catch (RemoteException e) {
                    try {
                        SendContactsInvitationServiceImpl.getInstance().ServerUnRegisterWithId(message.getFromUserId());
                        unRegisterUser(message.getFromUserId(), client);
                    } catch (RemoteException ex) {
                        System.out.println("exception in Client Service Impl line 109" + e.getMessage());
                    }
                }
            }
        }
    }

    private String getMessageFromBot(String body) {
        try {
            Document doc = Jsoup.parse(body);
            String textContent = doc.text();
            return ChatterBotService.getChatterBotResponse(textContent);
        } catch (Exception e) {
            return "sorry you friend is offline";
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

    private File readFileFromDirectory(Message message) {
        try {
            String absolutePath = message.getAttachment();
            Path filePath = Paths.get(absolutePath);

            // Check if the file exists before returning
            if (Files.exists(filePath)) {
                return filePath.toFile();
            } else {
                throw new FileNotFoundException("File not found: " + message.getBody());
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


    public void unRegisterUser(int userId) {
        //clients.remove(userId, client);
        clients.remove(userId);
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


    /*@Override
    public HashMap<Message, File> getMessagesAndFilesForRoomChat(int roomChatId) throws RemoteException {
        HashMap<Message, File> mesFiles = new HashMap<>();
        MessageDaoInterface messageDao = MessageDao.getInstance(MyDatabase.getInstance());
        try {
            List<Message> messages = messageDao.getAllByChatId(roomChatId);
            for (Message message : messages) {
                if (message.getAttachment() == null) {
                    mesFiles.put(message, null);
                } else {
                    File file = readFileFromDirectory(message);
                    mesFiles.put(message, file);
                }
            }
            return mesFiles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    @Override
    public HashMap<Message, byte[]> getMessagesAndFilesForRoomChat(int roomChatId) throws RemoteException {
        HashMap<Message, byte[]> mesFiles = new HashMap<>();
        MessageDaoInterface messageDao = MessageDao.getInstance(MyDatabase.getInstance());
        try {
            List<Message> messages = messageDao.getAllByChatId(roomChatId);
            for (Message message : messages) {
                if (message.getAttachment() == null) {
                    mesFiles.put(message, null);
                } else {
                    File file = readFileFromDirectory(message);
                    mesFiles.put(message, Converters.convertFileToBytes(file));
                }
            }
            return mesFiles;
        } catch (SQLException | IOException e) {
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

    @Override
    public List<User> getGroupMembers(int roomId) throws RemoteException{
        List<User> members;
        RoomChatDaoInterface roomDao = RoomChatDao.getInstance(MyDatabase.getInstance());
        try{
            members = roomDao.getAllUsers(roomId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return members;
    }

    @Override
    public void removeGroupMembers(int roomId, int memberId) throws RemoteException {
        RoomChatDaoInterface roomDao = RoomChatDao.getInstance(MyDatabase.getInstance());
        try {
            roomDao.removeRoomMember(roomId, memberId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void addRoomMember(int roomId, int memberId) throws RemoteException{
        RoomChatDaoInterface roomDao = RoomChatDao.getInstance(MyDatabase.getInstance());
        try{
            roomDao.addRoomMember(new RoomMember(roomId, memberId));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
