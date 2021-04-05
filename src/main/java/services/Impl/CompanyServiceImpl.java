package services.Impl;

import models.CompanyEntity;
import models.MedicineEntity;
import models.OrderEntity;
import repositories.CompanyRepository;
import repositories.Impl.CompanyRepositoryImpl;
import repositories.Impl.MedicineRepositoryImpl;
import repositories.Impl.OrderRepositoryImpl;
import repositories.MedicineRepository;
import repositories.OrderRepository;
import services.CompanyService;
import utils.DBCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CompanyServiceImpl implements CompanyService {
    @Override
    public ArrayList<CompanyEntity> getCompanies() throws SQLException {
        return new CompanyRepositoryImpl().get();
    }

    @Override
    public CompanyEntity getCompanyById(int id) throws SQLException {
        return new CompanyRepositoryImpl().getById(id);
    }

    @Override
    public boolean createCompany(HashMap<String, String[]> dict) throws SQLException {
        boolean result = false;
        CompanyRepository companyRepository = new CompanyRepositoryImpl();
        CompanyEntity company = setCompanyEntity(dict);

        try {
            if (companyRepository.create(company) > 0) result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean updateCompany(HashMap<String, String[]> dict) throws SQLException {
        boolean result = false;
        CompanyRepository companyRepository = new CompanyRepositoryImpl();
        CompanyEntity company = setCompanyEntity(dict);
        company.setId(Integer.parseInt(dict.get("id")[0]));

        try {
            if (companyRepository.update(company) > 0) result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean deleteCompanyById(int id) throws SQLException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        CompanyRepository companyRepository = new CompanyRepositoryImpl();
        OrderRepository orderRepository = new OrderRepositoryImpl();
        MedicineRepository medicineRepository = new MedicineRepositoryImpl();

        try {
            ArrayList<MedicineEntity> medicines = medicineRepository.getByCompanyId(id);
            ArrayList<OrderEntity>[] orders = new ArrayList[medicines.size()];

            for (int i = 0; i < medicines.size(); i++) {
                orders[i] = orderRepository.getByMedicineId(medicines.get(i).getId());
            }

            connection = DBCPDataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("");

            for (int i = 0; i < medicines.size(); i++) {
                for (OrderEntity order : orders[i]) {
                    orderRepository.delete(order.getId(), connection, statement);
                }

                medicineRepository.delete(medicines.get(i).getId(), connection, statement);
            }

            companyRepository.delete(id, connection, statement);
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

    private CompanyEntity setCompanyEntity(HashMap<String, String[]> dict) {
        CompanyEntity company = new CompanyEntity();

        try {
            company.setName(dict.get("name")[0]);
            company.setDescription(dict.get("description")[0]);
            company.setEmail(dict.get("email")[0]);
            company.setAddress(dict.get("address")[0]);
            company.setPhone(dict.get("phone")[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return company;
    }
}
