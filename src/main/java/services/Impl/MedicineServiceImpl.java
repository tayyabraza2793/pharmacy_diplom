package services.Impl;

import models.MedicineEntity;
import models.OrderEntity;
import repositories.Impl.MedicineRepositoryImpl;
import repositories.Impl.OrderRepositoryImpl;
import repositories.MedicineRepository;
import repositories.OrderRepository;
import services.MedicineService;
import utils.DBCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MedicineServiceImpl implements MedicineService {
    @Override
    public ArrayList<MedicineEntity> getMedicines() throws SQLException {
        return new MedicineRepositoryImpl().get();
    }

    @Override
    public MedicineEntity getMedicineById(int id) throws SQLException {
        return new MedicineRepositoryImpl().getById(id);
    }

    @Override
    public boolean createMedicine(HashMap<String, String[]> dict) {
        boolean result = false;
        MedicineRepository medicineRepository = new MedicineRepositoryImpl();
        MedicineEntity medicine = setMedicineEntity(dict);

        try {
            if (medicineRepository.create(medicine) > 0) result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean updateMedicine(HashMap<String, String[]> dict) throws SQLException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        MedicineRepository medicineRepository = new MedicineRepositoryImpl();
        OrderRepository orderRepository = new OrderRepositoryImpl();
        MedicineEntity medicine = setMedicineEntity(dict);
        medicine.setId(Integer.parseInt(dict.get("id")[0]));

        try {
            connection = DBCPDataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("");
            ArrayList<OrderEntity> orders = orderRepository.getByMedicineId(medicine.getId());

            for (OrderEntity order : orders) {
                order.setSum(order.getQuantity() * medicine.getSellPrice());
                orderRepository.update(order, connection, statement);
            }

            medicineRepository.update(medicine, connection, statement);
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
    public boolean deleteMedicineById(int id) throws SQLException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        MedicineRepository medicineRepository = new MedicineRepositoryImpl();
        OrderRepository orderRepository = new OrderRepositoryImpl();

        try {
            connection = DBCPDataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("");
            ArrayList<OrderEntity> orders = orderRepository.getByMedicineId(id);

            for (OrderEntity order : orders) {
                orderRepository.delete(order.getId(), connection, statement);
            }

            medicineRepository.delete(id, connection, statement);
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

    private MedicineEntity setMedicineEntity(HashMap<String, String[]> dict) {
        MedicineEntity medicine = new MedicineEntity();

        try {
            medicine.setCompanyId(Integer.parseInt(dict.get("company_id")[0]));
            medicine.setName(dict.get("name")[0]);
            medicine.setType(dict.get("type")[0]);
            medicine.setManDate(dict.get("man_date")[0]);
            medicine.setExpDate(dict.get("exp_date")[0]);
            medicine.setBuyPrice(Integer.parseInt(dict.get("buy_price")[0]));
            medicine.setSellPrice(Integer.parseInt(dict.get("sell_price")[0]));
            medicine.setIndex(Integer.parseInt(dict.get("index")[0]));
            medicine.setQuantity(Integer.parseInt(dict.get("quantity")[0]));
            medicine.setTabletsCount(Integer.parseInt(dict.get("tablets_count")[0]));
            medicine.setDosage(dict.get("dosage")[0]);
            medicine.setDescription(dict.get("description")[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return medicine;
    }
}
