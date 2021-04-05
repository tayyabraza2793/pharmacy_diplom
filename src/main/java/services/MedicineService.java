package services;

import models.MedicineEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface MedicineService {
    ArrayList<MedicineEntity> getMedicines() throws SQLException;

    MedicineEntity getMedicineById(int id) throws SQLException;

    boolean createMedicine(HashMap<String, String[]> dict);

    boolean updateMedicine(HashMap<String, String[]> dict) throws SQLException;

    boolean deleteMedicineById(int id) throws SQLException;
}
