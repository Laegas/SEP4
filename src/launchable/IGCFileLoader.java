package launchable;

import database.DAO.DaoManager;
import database.DAO.IGCDataDAO;
import fileUtil.FileDecoder;
import model.igc.Flight;

import java.io.File;

/**
 * Created by kenneth on 17/05/2018.
 */
public class IGCFileLoader {

    private static final File dirWithIGCLogFiles = new File("C:\\igc");

    public static void main(String[] args) {

        File[] IGC_files = dirWithIGCLogFiles.listFiles();

        FileDecoder fileDecoder;
        Flight dataLogger;
        IGCDataDAO igc_dao = DaoManager.IGC_SOURCE_DAO;

        System.out.println("loading #" + IGC_files.length + " files");
        int counter = 1;

        for (File file : IGC_files) {
            fileDecoder = new FileDecoder(file.getAbsolutePath());
            dataLogger = fileDecoder.readFile();
            igc_dao.insertDataLogger(dataLogger);
            System.out.println("finished loading file #" + counter++);
        }

    }
}
