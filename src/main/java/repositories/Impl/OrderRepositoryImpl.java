package repositories.Impl;

import models.OrderEntity;
import repositories.OrderRepository;
import utils.ResultSetConvertService;
import utils.DBCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderRepositoryImpl implements OrderRepository {
    private static final String SQL_SELECT = "SELECT * FROM [dbo].[Order]";
    private static final String SQL_SELECT_ID = "SELECT * FROM [dbo].[Order] WHERE Id = ?";
    private static final String SQL_SELECT_USER_ID = "SELECT * FROM [dbo].[Order] WHERE User_Id = ?";
    private static final String SQL_SELECT_MEDICINE_ID = "SELECT * FROM [dbo].[Order] WHERE Medicine_Id = ?";
    private static final String SQL_CREATE = "INSERT INTO [dbo].[Order] (User_Id, Medicine_Id, Quantity, Sum, Status)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_STATUS_SUM = "UPDATE [dbo].[Order] SET Status = ?, Sum = ? WHERE Id = ?";
    private static final String SQL_DELETE_ID = "DELETE FROM [dbo].[Order] WHERE Id = ?";
    private static ResultSetConvertService converter;

    public OrderRepositoryImpl() {
        if (converter == null) {
            converter = new ResultSetConvertService<OrderEntity>(OrderEntity.class);
        }
    }

    @Override
    public ArrayList<OrderEntity> get() throws SQLException {
        ArrayList<OrderEntity> orders = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBCPDataSource.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery();
            orders = converter.convertResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }

        return orders;
    }

    @Override
    public OrderEntity getById(int id) throws SQLException {
        ArrayList<OrderEntity> orders = getOrderEntities(id, SQL_SELECT_ID);
        OrderEntity order = null;

        if (!orders.isEmpty()) order = orders.get(0);

        return order;
    }

    @Override
    public ArrayList<OrderEntity> getByUserId(int userId) throws SQLException {
        return getOrderEntities(userId, SQL_SELECT_USER_ID);
    }

    @Override
    public ArrayList<OrderEntity> getByMedicineId(int medicineId) throws SQLException {
        return getOrderEntities(medicineId, SQL_SELECT_MEDICINE_ID);
    }

    private ArrayList<OrderEntity> getOrderEntities(int id, String sql) throws SQLException {
        ArrayList<OrderEntity> orders = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBCPDataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            orders = converter.convertResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return orders;
    }

    @Override
    public int create(OrderEntity order, Connection connection, PreparedStatement statement) throws SQLException {
        int result = 0;
        statement = converter.convertStatement(connection.prepareStatement(SQL_CREATE), order, 0);
        result = statement.executeUpdate();
        return result;
    }

    @Override
    public int update(OrderEntity order, Connection connection, PreparedStatement statement) throws SQLException {
        int result = 0;
        statement = connection.prepareStatement(SQL_UPDATE_STATUS_SUM);
        statement.setString(1, order.getStatus());
        statement.setInt(2, order.getSum());
        statement.setInt(3, order.getId());
        result = statement.executeUpdate();
        return result;
    }

    @Override
    public int delete(int id, Connection connection, PreparedStatement statement) throws SQLException {
        int result = 0;
        statement = connection.prepareStatement(SQL_DELETE_ID);
        statement.setInt(1, id);
        result = statement.executeUpdate();
        return result;
    }
}


//----------For create
//statement = connection.prepareStatement(SQL_UPDATE_MEDICINE);
//            statement.setInt(1, -1 * order.getQuantity());
//            statement.setInt(1, order.getMedicineId());
//            statement.executeUpdate();
//    private static final String SQL_UPDATE_MEDICINE = "UPDATE [dbo].[Medicine] SET Quantity = Quantity + ? WHERE Id = ?";
//----------
//
//    @Override
//    public int deleteUserOrder(int userId, Connection connection, PreparedStatement statement) throws SQLException {
//        int result = 0;
//        ResultSet resultSet = null;
//        statement = connection.prepareStatement(SQL_SELECT_USER_ID);
//        statement.setInt(1, userId);
//        resultSet = statement.executeQuery();
//        List<OrderEntity> orders = converter.convertResultSet(resultSet);
//        for (OrderEntity order : orders) {
//            result += delete(order.getId(), connection, statement);
//        }
//        return result;
//    }
//----------For delete
//ResultSet resultSet = null;
//        statement = connection.prepareStatement(SQL_SELECT_ID);
//                statement.setInt(1, id);
//                resultSet = statement.executeQuery();
//                List<OrderEntity> order = converter.convertResultSet(resultSet);
//        statement = connection.prepareStatement(SQL_UPDATE_MEDICINE);
//        statement.setInt(1, order.get(0).getQuantity());
//        statement.setInt(2, order.get(0).getMedicineId());
//        statement.executeUpdate();
//        statement = connection.prepareStatement(SQL_DELETE_ID);
//        statement.setInt(1, id);
//        result = statement.executeUpdate();
//----------For update
//try {
//        connection = DBCPDataSource.getConnection();
//        connection.setAutoCommit(false);
//
//        if (order.getStatus().equals("Cancelled")) {
//        statement = connection.prepareStatement(SQL_UPDATE_MEDICINE);
//        statement.setInt(1, order.getQuantity());
//        statement.setInt(2, order.getMedicineId());
//        statement.executeUpdate();
//        }
//
//        statement = connection.prepareStatement(SQL_SELECT_MEDICINE);
//        statement.setInt(1, order.getMedicineId());
//        resultSet = statement.executeQuery();
//        ArrayList<MedicineEntity> medicines = converter.convertResultSet(resultSet);
//        if (!medicines.isEmpty()) medicine = medicines.get(0);
//        statement = connection.prepareStatement(SQL_UPDATE_STATUS_SUM);
//        statement.setString(1, order.getStatus());
//        statement.setInt(2, order.getQuantity() * medicine.getSellPrice());
//        statement.setInt(3, order.getId());
//        result = statement.executeUpdate();
//        connection.commit();
//        } catch (Exception e) {
//        if (connection != null) connection.rollback();
//        } finally {
//        if (connection != null) connection.close();
//        if (statement != null) statement.close();
//        if (resultSet != null) resultSet.close();
//        }
//
//        return result;
//
//----------For getById
//
//    OrderEntity order = null;
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = DBCPDataSource.getConnection();
//            statement = connection.prepareStatement(SQL_SELECT_ID);
//            statement.setInt(1, id);
//            resultSet = statement.executeQuery();
//            ArrayList<OrderEntity> orders = converter.convertResultSet(resultSet);
//            if (!orders.isEmpty()) order = orders.get(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) connection.close();
//            if (statement != null) statement.close();
//            if (resultSet != null) resultSet.close();
//        }
//
//        return order;