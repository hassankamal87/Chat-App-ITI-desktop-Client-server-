package com.whisper.server.business.services;

import com.whisper.server.business.services.interfaces.UsersServiceInt;
import com.whisper.server.persistence.daos.UserDao;
import com.whisper.server.persistence.db.MyDatabase;
import javafx.collections.ObservableList;
import org.example.entities.User;

import java.sql.SQLException;
import java.util.List;

public class ClientsProfileService{
    private UsersServiceInt usersServiceInt;

    private static ClientsProfileService clientsProfile;
    private ObservableList<User> observableList;
    private ClientsProfileService(){}
    public static synchronized ClientsProfileService getInstance(){
        if(clientsProfile == null){
            clientsProfile = new ClientsProfileService();
        }
        return clientsProfile;
    }

    public void setUsersServiceInt(UsersServiceInt usersServiceInt) throws SQLException {
        this.usersServiceInt = usersServiceInt;

        updateDataFromDb();
    }

    public void updateDataFromDb() {
        try {
            usersServiceInt.updateDataFromDb(UserDao.getInstance(MyDatabase.getInstance()).getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        return UserDao.getInstance(MyDatabase.getInstance()).getAll();
    }
    public void updateUser(User user) throws SQLException {
        UserDao.getInstance(MyDatabase.getInstance()).updateUser(user);
    }
    public void deleteUser(User user) throws SQLException {
        UserDao.getInstance(MyDatabase.getInstance()).deleteById(user.getUserId());
    }


}
