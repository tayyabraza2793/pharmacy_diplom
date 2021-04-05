package repositories.Impl;

import models.MedicineEntity;
import repositories.MedicineRepository;
import utils.ResultSetConvertService;
import utils.DBCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicineRepositoryImpl implements MedicineRepository {
    private static final String SQL_SELECT = "SELECT * FROM [dbo].[Medicine]";
    private static final String SQL_SELECT_ID = "SELECT * FROM [dbo].[Medicine] WHERE Id = ?";
    private static final String SQL_SELECT_COMPANY_ID = "SELECT * FROM [dbo].[Medicine] WHERE Company_Id = ?";
    private static final String SQL_CREATE = "IF NOT EXISTS (SELECT * FROM [dbo].[Medicine] WHERE Name = ?)" +
            "INSERT INTO [dbo].[Medicine] (Company_Id, Name, Type, Man_Date, Exp_Date," +
            "Buy_Price, Sell_Price, [Index], Quantity, Tablets_Count, Dosage, Description) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE [dbo].[Medicine] SET Company_Id = ?, Name = ?, Type = ?," +
            "Man_Date = ?, Exp_Date = ?, Buy_Price = ?, Sell_Price = ?, [Index] = ?, Quantity = ?, Tablets_Count = ?," +
            "Dosage = ?, Description = ? WHERE Id = ?";
    private static final String SQL_DELETE_ID = "DELETE FROM [dbo].[Medicine] WHERE Id = ?";
    private static ResultSetConvertService converter;

    public MedicineRepositoryImpl() {
        if (converter == null) {
            converter = new ResultSetConvertService<MedicineEntity>(MedicineEntity.class);
        }
    }

    @Override
    public ArrayList<MedicineEntity> get() throws SQLException {
        ArrayList<MedicineEntity> medicines = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBCPDataSource.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery();
            medicines = converter.convertResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }

        return medicines;
    }

    @Override
    public MedicineEntity getById(int id) throws SQLException {
        MedicineEntity medicine = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBCPDataSource.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            ArrayList<MedicineEntity> medicines = converter.convertResultSet(resultSet);
            if (!medicines.isEmpty()) medicine = medicines.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }

        return medicine;
    }

    @Override
    public ArrayList<MedicineEntity> getByCompanyId(int companyId) throws SQLException {
        ArrayList<MedicineEntity> medicines = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBCPDataSource.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_COMPANY_ID);
            statement.setInt(1, companyId);
            resultSet = statement.executeQuery();
            medicines = converter.convertResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return medicines;
    }

    @Override
    public int create(MedicineEntity medicine) throws SQLException {
        int result = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBCPDataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_CREATE);
            statement.setString(1, medicine.getName());
            statement = converter.convertStatement(statement, medicine, 1);
            result = statement.executeUpdate();
            connection.commit();
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
    public int update(MedicineEntity medicine, Connection connection, PreparedStatement statement) throws SQLException {
        int result = 0;
        statement = connection.prepareStatement(SQL_UPDATE);
        statement = converter.convertStatement(statement, medicine, 0);
        statement.setInt(13, medicine.getId());
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
