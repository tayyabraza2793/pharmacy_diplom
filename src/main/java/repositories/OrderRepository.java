package repositories;

import models.OrderEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderRepository {
    ArrayList<OrderEntity> get() throws SQLException;

    OrderEntity getById(int id) throws SQLException;

    ArrayList<OrderEntity> getByUserId(int userId) throws SQLException;

    ArrayList<OrderEntity> getByMedicineId(int medicineId) throws SQLException;

    int create(OrderEntity order, Connection connection, PreparedStatement statement) throws SQLException;

    int update(OrderEntity order, Connection connection, PreparedStatement statement) throws SQLException;

    int delete(int id, Connection connection, PreparedStatement statement) throws SQLException;

//    int deleteUserOrder(int userId, Connection connection, PreparedStatement statement) throws SQLException;
}
