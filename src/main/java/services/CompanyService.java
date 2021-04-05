package services;

import models.CompanyEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface CompanyService {
    ArrayList<CompanyEntity> getCompanies() throws SQLException;

    CompanyEntity getCompanyById(int id) throws SQLException;

    boolean createCompany(HashMap<String, String[]> dict) throws SQLException;

    boolean updateCompany(HashMap<String, String[]> dict) throws SQLException;

    boolean deleteCompanyById(int id) throws SQLException;
}
