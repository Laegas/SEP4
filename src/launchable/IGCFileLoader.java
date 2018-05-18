package launchable;

import database.DAO.IGCDataDAO;
import database.DAO.IGCSourceDao;
import fileUtil.FileDecoder;
import model.igc.Flight;

import java.io.File;

/**
 * Created by kenneth on 17/05/2018.
 */
public class IGCFileLoader {

    private static final File dirWithIGCLogFiles = new File("C:\\IGC_files");

    public static void main(String[] args) {

        //getting all files in the directory

        File[] IGC_files = dirWithIGCLogFiles.listFiles();

        FileDecoder fileDecoder;
        Flight flight;
//        IGCDataDAO igc_dao = DaoManager.IGC_DAO; right approach TODO
        IGCDataDAO igc_dao = new IGCSourceDao() ; // bad approach

        for (File file : IGC_files) {
            fileDecoder = new FileDecoder(file.getAbsolutePath());
            flight = fileDecoder.readFile();

            igc_dao.insertDataLogger(flight);

        }

    }
}
