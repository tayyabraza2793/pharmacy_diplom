package services.Impl;

import models.MedicineEntity;
import models.OrderEntity;
import repositories.Impl.MedicineRepositoryImpl;
import repositories.Impl.OrderRepositoryImpl;
import repositories.MedicineRepository;
import repositories.OrderRepository;
import services.OrderService;
import utils.DBCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderServiceImpl implements OrderService {
    @Override
    public ArrayList<OrderEntity> getOrders() throws SQLException {
        return new OrderRepositoryImpl().get();
    }

    public ArrayList<OrderEntity> getOrdersByUserId(int userId) throws SQLException {
        return new OrderRepositoryImpl().getByUserId(userId);
    }

    @Override
    public OrderEntity getOrderById(int id) throws SQLException {
        return new OrderRepositoryImpl().getById(id);
    }

    @Override
    public boolean createOrder(HashMap<String, String[]> dict) throws SQLException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        OrderRepository orderRepository = new OrderRepositoryImpl();
        MedicineRepository medicineRepository = new MedicineRepositoryImpl();
        OrderEntity order = setOrderEntity(dict);
        order.setStatus("Ready");

        try {
            connection = DBCPDataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("");
            MedicineEntity medicine = medicineRepository.getById(order.getMedicineId());
            if (medicine.getQuantity() < order.getQuantity()) return false;
            medicine.setQuantity(medicine.getQuantity() - order.getQuantity());
            medicineRepository.update(medicine, connection, statement);
            order.setSum(medicine.getSellPrice() * order.getQuantity());
            orderRepository.create(order, connection, statement);
            connection.commit();
            result = true;
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
        }

        return result;
    }

    @Override
    public boolean updateOrderStatus(HashMap<String, String[]> dict) throws SQLException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        OrderRepository orderRepository = new OrderRepositoryImpl();
        MedicineRepository medicineRepository = new MedicineRepositoryImpl();
        OrderEntity order = setOrderEntity(dict);
        order.setId(Integer.parseInt(dict.get("id")[0]));
        order.setSum(Integer.parseInt(dict.get("sum")[0]));
        order.setStatus("Cancelled");

        try {
            connection = DBCPDataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("");
            MedicineEntity medicine = medicineRepository.getById(order.getMedicineId());
            medicine.setQuantity(medicine.getQuantity() + order.getQuantity());
            medicineRepository.update(medicine, connection, statement);
            orderRepository.update(order, connection, statement);
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

    @Override
    public boolean deleteOrderById(int id) throws SQLException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        OrderRepository orderRepository = new OrderRepositoryImpl();
        MedicineRepository medicineRepository = new MedicineRepositoryImpl();

        try {
            connection = DBCPDataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("");
            OrderEntity order = orderRepository.getById(id);
            if (!order.getStatus().equals("Cancelled")) {
                MedicineEntity medicine = medicineRepository.getById(order.getMedicineId());
                medicine.setQuantity(medicine.getQuantity() + order.getQuantity());
                medicineRepository.update(medicine, connection, statement);
            }
            orderRepository.delete(id, connection, statement);
            connection.commit();
            result = true;
        } catch (Exception e) {
            if (connection != null) connection.rollback();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return result;
    }

    private OrderEntity setOrderEntity(HashMap<String, String[]> dict) {
        OrderEntity order = new OrderEntity();

        try {
            order.setUserId(Integer.parseInt(dict.get("userId")[0]));
            order.setMedicineId(Integer.parseInt(dict.get("medicineId")[0]));
            order.setQuantity(Integer.parseInt(dict.get("quantity")[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return order;
    }
}
