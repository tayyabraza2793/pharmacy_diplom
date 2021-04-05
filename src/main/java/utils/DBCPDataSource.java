package utils;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {
    private static BasicDataSource dataSource;

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            BasicDataSource ds = new BasicDataSource();
            ds.setUrl("jdbc:sqlserver://localhost\\MSSQLSERVER;database=Pharmacy");
            ds.setUsername("pharmacy");
            ds.setPassword("pharmacy");
            ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ds.setMinIdle(10);
            ds.setMaxIdle(20);
            ds.setMaxOpenPreparedStatements(100);
            dataSource = ds;
        }

        return dataSource.getConnection();
    }
}
