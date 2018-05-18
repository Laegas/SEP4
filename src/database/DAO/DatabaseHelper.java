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
                    DatabaseConfig.CONNECTION_STRING,
                    DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    static DatabaseHelper getInstance() {
        if(instance == null)
            instance = new DatabaseHelper();
        return instance;
    }

    Connection getConnection() {
        return connection;
    }


}

