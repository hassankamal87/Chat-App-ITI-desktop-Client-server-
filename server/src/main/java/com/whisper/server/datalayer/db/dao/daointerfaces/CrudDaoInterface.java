package com.whisper.server.datalayer.db.dao.daointerfaces;

import java.sql.SQLException;
import java.util.List;

public interface CrudDaoInterface <T>{
    int create (T object) throws SQLException;
    T getById (int id) throws SQLException;
    int update (T object) throws SQLException;
    int deleteById (int id) throws SQLException;
    List<T> getAll() throws SQLException;
}
