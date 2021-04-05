package services;

import models.UserEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface UserService {
    ArrayList<UserEntity> getUsers() throws SQLException;

    UserEntity login(String email, String password) throws SQLException;

    ArrayList<String> registerValidation(HashMap<String, String[]> dict);

    String getPath(String role);

    UserEntity getUserByEmail(String email) throws SQLException;

    boolean createUser(HashMap<String, String[]> dict);

    boolean updateUser(HashMap<String, String[]> dict);

    boolean deleteUserById(int id) throws SQLException;
}

//    boolean createUser(UserEntity user) throws SQLException;