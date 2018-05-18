package launchable;

import database.DAO.IGCDataDAO;
import database.DAO.IGCSourceDao;
import fileUtil.FileDecoder;
import model.igc.Flight;
import model.igc.Flight;

import java.io.File;

/**
 * Created by kenneth on 17/05/2018.
 */
public class IGCFileLoader {

    private static final File dirWithIGCLogFiles = new File("C:\\IGC_files");

    public static void main(String[] args) {

        File[] IGC_files = dirWithIGCLogFiles.listFiles();

        FileDecoder fileDecoder;
        Flight dataLogger;
        IGCDataDAO igc_dao = new IGCSourceDao();
        if(IGC_files.length > 0)
        for (File file : IGC_files) {
            fileDecoder = new FileDecoder(file.getAbsolutePath());
            dataLogger = fileDecoder.readFile();

            igc_dao.insertDataLogger(dataLogger);

        }

    }
}
