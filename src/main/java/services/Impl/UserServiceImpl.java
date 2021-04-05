package services.Impl;

import models.MedicineEntity;
import models.OrderEntity;
import models.UserEntity;
import repositories.Impl.MedicineRepositoryImpl;
import repositories.Impl.OrderRepositoryImpl;
import repositories.Impl.UserRepositoryImpl;
import repositories.MedicineRepository;
import repositories.OrderRepository;
import repositories.UserRepository;
import services.UserService;
import utils.DBCPDataSource;
import utils.FormValidationService;
import utils.HashingService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserServiceImpl implements UserService {
    @Override
    public ArrayList<UserEntity> getUsers() throws SQLException {
        return new UserRepositoryImpl().get();
    }

    @Override
    public UserEntity login(String email, String password) {
        UserRepository repository = new UserRepositoryImpl();
        HashingService hs = new HashingService();
        UserEntity user = null;

        try {
            user = repository.getByEmail(email);
            if (user != null) {
                if (!hs.hashPasswordWithoutSalt(password, user.getSalt()).equals(user.getPassword())) {
                    user = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public String getPath(String role) {
        switch (role) {
            case "admin": {
                return "/adminPage";
            }
            case "pharmacist": {
                return "/pharmacistPage";
            }
            case "customer": {
                return "/customerPage";
            }
            default: {
                return "/loginPage";
            }
        }
    }

    @Override
    public ArrayList<String> registerValidation(HashMap<String, String[]> dict) {
        FormValidationService vs = new FormValidationService();
        ArrayList<String> exceptions = vs.validate(dict);
        String passwordValidation = vs.validatePassword(dict.get("password")[0], dict.get("confirm password")[0]);
        if (passwordValidation != null) exceptions.add(passwordValidation);
        return exceptions;
    }

    @Override
    public UserEntity getUserByEmail(String email) throws SQLException {
        return new UserRepositoryImpl().getByEmail(email);
    }

    @Override
    public boolean createUser(HashMap<String, String[]> dict) {
        boolean result = false;
        UserRepository userRepository = new UserRepositoryImpl();
        UserEntity user = setUserEntity(dict);

        try {
            if (userRepository.create(user) > 0) result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean updateUser(HashMap<String, String[]> dict) {
        boolean result = false;
        UserRepository userRepository = new UserRepositoryImpl();
        UserEntity user = setUserEntity(dict);
        user.setId(Integer.parseInt(dict.get("id")[0]));

        try {
            if (userRepository.update(user) > 0) result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean deleteUserById(int id) throws SQLException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        UserRepository userRepository = new UserRepositoryImpl();
        OrderRepository orderRepository = new OrderRepositoryImpl();
        MedicineRepository medicineRepository = new MedicineRepositoryImpl();

        try {
            connection = DBCPDataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("");
            ArrayList<OrderEntity> orders = orderRepository.getByUserId(id);

            for (OrderEntity order : orders) {
                MedicineEntity medicine = medicineRepository.getById(order.getMedicineId());
                medicine.setQuantity(medicine.getQuantity() + order.getQuantity());
                medicineRepository.update(medicine, connection, statement);
                orderRepository.delete(order.getId(), connection, statement);
            }

            userRepository.delete(id, connection, statement);
            connection.commit();
            result = true;
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return result;
    }

    private UserEntity setUserEntity(HashMap<String, String[]> dict) {
        UserEntity user = new UserEntity();
        HashingService hashingService = new HashingService();

        try {
            String[] hashDetails = hashingService.hashPassword(dict.get("password")[0]);
            user.setName(dict.get("name")[0]);
            user.setSecondName(dict.get("second name")[0]);
            user.setEmail(dict.get("email")[0]);
            user.setPhone(dict.get("phone")[0]);
            user.setSalt(hashDetails[0]);
            user.setPassword(hashDetails[1]);
            user.setRole(dict.get("role")[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

}

//        @Override
//    public boolean createUser(UserEntity user) throws SQLException {
//        UserRepository repository = new UserRepositoryImpl();
//        if (repository.create(user) <= 0) return false;
//        return true;
//    }