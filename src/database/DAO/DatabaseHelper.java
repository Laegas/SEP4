package database.DAO;

import config.DatabaseConfig;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DatabaseHelper<T> {
    private static Connection connection;

    static{
        try {
            connection = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DatabaseHelper()
    {
    }

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(
                DatabaseConfig.CONNECTION_STRING,
                DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD);
    }

    private PreparedStatement prepare(Connection connection, String sql,
                                      Object[] parameters) throws SQLException
    {
        PreparedStatement stat = connection.prepareStatement(sql);
        for (int i = 0; i < parameters.length; i++)
        {
            stat.setObject(i + 1, parameters[i]);
        }
        return stat;
    }

    public int executeUpdate(String sql, Object... parameters)
    {
        try
        {
            PreparedStatement stat = prepare(connection, sql, parameters);
            return stat.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public T mapSingle(DataMapper<T> mapper, String sql, Object... parameters)
    {
        try
        {
            PreparedStatement stat = prepare(connection, sql, parameters);
            ResultSet rs = stat.executeQuery();
            if (rs.next())
            {
                return mapper.create(rs);
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> map(DataMapper<T> mapper, String sql, Object... parameters)
    {
        LinkedList<T> allRows = new LinkedList<>();
        try (Connection connection = getConnection())
        {
            PreparedStatement stat = prepare(connection, sql, parameters);
            ResultSet rs = stat.executeQuery();
            while (rs.next())
            {
                allRows.add(mapper.create(rs));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return allRows;
    }
}

