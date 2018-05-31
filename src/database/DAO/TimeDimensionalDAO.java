package database.DAO;

import model.time.Date;
import model.time.Time;

public interface TimeDimensionalDAO {

    Time getTimeByID(int id);
    Date getDateByID(int id);
}
