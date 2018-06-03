package database.DAO;

import model.time.Date;
import model.time.Time;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeDimensionalDAOImp implements TimeDimensionalDAO {

    private Connection connection;
    TimeDimensionalDAOImp()
    {
        this.connection=DatabaseHelper.getInstance().getConnection();
    }
    @Override
    public Time getTimeByID(int id) {
        Time time = null;
        PreparedStatement stmt = null;
        try{
            String sql = "SELECT hour, minute, second from D_TIME WHERE ID_TIME = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1,id);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            time = new Time(Integer.parseInt(resultSet.getString("hour")),Integer.parseInt(resultSet.getString("minute")),Integer.parseInt(resultSet.getString("second")));
        }
       catch(SQLException e)
       {
           e.printStackTrace();
       }finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       return time;
    }

    @Override
    public Date getDateByID(int id) {
    Date date = null;
        String sql = "select year,month,day from D_DATE where ID_DATE =?";
        try {
           PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            date = new Date(Integer.parseInt(rs.getString("day")),Integer.parseInt(rs.getString("month")), Integer.parseInt(rs.getString("year")));
            stm.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return date;
    }
}
