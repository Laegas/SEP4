package database.DAO;

import config.DatabaseConfig;

import java.sql.*;

class DatabaseHelper {

    private static DatabaseHelper instance;
    private static Connection connection;

    private DatabaseHelper()
    {
        try {
            connection = DriverManager.getConnection(
                    DatabaseConfig.INSTANCE.getCONNECTION_STRING(),
                    DatabaseConfig.INSTANCE.getUSERNAME(), DatabaseConfig.INSTANCE.getPASSWORD());
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    static synchronized DatabaseHelper getInstance() {
        if(instance == null)
            instance = new DatabaseHelper();
        return instance;
    }

    Connection getConnection() {
        return connection;
    }


}

