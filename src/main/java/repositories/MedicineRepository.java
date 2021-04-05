package repositories;


import models.MedicineEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface MedicineRepository {
    ArrayList<MedicineEntity> get() throws SQLException;

    MedicineEntity getById(int id) throws SQLException;

    ArrayList<MedicineEntity> getByCompanyId(int companyId) throws SQLException;

    int create(MedicineEntity medicine) throws SQLException;

    int update(MedicineEntity medicine, Connection connection, PreparedStatement statement) throws SQLException;

    int delete(int id, Connection connection, PreparedStatement statement) throws SQLException;
}
