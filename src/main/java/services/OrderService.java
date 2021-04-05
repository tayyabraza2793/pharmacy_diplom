package services;

import models.OrderEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface OrderService {
    ArrayList<OrderEntity> getOrders() throws SQLException;

    OrderEntity getOrderById(int id) throws SQLException;

    boolean createOrder(HashMap<String, String[]> dict) throws SQLException;

    boolean updateOrderStatus(HashMap<String, String[]> dict) throws SQLException;

    boolean deleteOrderById(int id) throws SQLException;
}
