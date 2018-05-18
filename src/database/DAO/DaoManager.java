package database.DAO;

/**
 * Created by kenneth on 18/05/2018.
 */
public class DaoManager {
    public static final IGCDataDAO IGC_SOURCE_DAO = new IGCSourceDao();
    public static final IGCDimensionalDao IGC_DIMENSIONAL_DAO = new IGCDimensionalDaoImp();
}
