package repositories.Impl;

import models.CompanyEntity;
import repositories.CompanyRepository;
import utils.ResultSetConvertService;
import utils.DBCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyRepositoryImpl implements CompanyRepository {
    private static final String SQL_SELECT = "SELECT * FROM [dbo].[Company]";
    private static final String SQL_SELECT_ID = "SELECT * FROM [dbo].[Company] WHERE Id = ?";
    private static final String SQL_CREATE = "IF NOT EXISTS (SELECT * FROM [dbo].[Company] WHERE Name = ?)" +
            "INSERT INTO [dbo].[Company] (Name, Description, Email, Address, Phone) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE [dbo].[Company] SET Name = ?, Description = ?, Email = ?, Address = ?, Phone = ? WHERE Id = ?";
    private static final String SQL_DELETE_ID = "DELETE FROM [dbo].[Company] WHERE Id = ?";
    private static ResultSetConvertService converter;

    public CompanyRepositoryImpl() {
        if (converter == null) {
            converter = new ResultSetConvertService<CompanyEntity>(CompanyEntity.class);
        }
    }

    @Override
    public ArrayList<CompanyEntity> get() throws SQLException {
        ArrayList<CompanyEntity> companies = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBCPDataSource.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery();
            companies = converter.convertResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }

        return companies;
    }

    @Override
    public CompanyEntity getById(int id) throws SQLException {
        CompanyEntity company = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBCPDataSource.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            ArrayList<CompanyEntity> companies = converter.convertResultSet(resultSet);
            if (!companies.isEmpty()) company = companies.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }

        return company;
    }

    @Override
    public int create(CompanyEntity company) throws SQLException {
        int result = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBCPDataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_CREATE);
            statement.setString(1, company.getName());
            statement = converter.convertStatement(statement, company, 1);
            result = statement.executeUpdate();
            connection.commit();
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
    public int update(CompanyEntity company) throws SQLException {
        int result = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBCPDataSource.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement = converter.convertStatement(statement, company, 0);
            statement.setInt(6, company.getId());
            result = statement.executeUpdate();
            connection.commit();
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
    public int delete(int id, Connection connection, PreparedStatement statement) throws SQLException {
        int result = 0;
        statement = connection.prepareStatement(SQL_DELETE_ID);
        statement.setInt(1, id);
        result = statement.executeUpdate();
        return result;
    }
}
