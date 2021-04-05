package repositories;

import models.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserRepository {
    ArrayList<UserEntity> get() throws SQLException;

    UserEntity getByEmail(String email) throws SQLException;

    int create(UserEntity user) throws SQLException;

    int update(UserEntity user) throws SQLException;

    int delete(int id, Connection connection, PreparedStatement statement) throws SQLException;
}
