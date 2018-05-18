package database.DAO;

import config.DatabaseConfig;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DatabaseHelper {

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

    public static DatabaseHelper getInstance() {
        if(instance == null)
            instance = new DatabaseHelper();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }


}

