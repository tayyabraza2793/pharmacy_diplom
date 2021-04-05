package repositories;

import models.CompanyEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CompanyRepository {
    ArrayList<CompanyEntity> get() throws SQLException;

    CompanyEntity getById(int id) throws SQLException;

    int create(CompanyEntity company) throws SQLException;

    int update(CompanyEntity company) throws SQLException;

    int delete(int id, Connection connection, PreparedStatement statement) throws SQLException;
}
