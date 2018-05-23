package launchable;

import config.FileConfig;
import database.DAO.DaoManager;
import database.DAO.IGCDataDAO;
import fileUtil.FileDecoder;
import model.igc.Flight;

import java.io.File;

/**
 * Created by kenneth on 17/05/2018.
 */
public class IGCFileLoader {

    private static final File dirWithIGCLogFiles = new File(FileConfig.IGC_DIRECTORY_PATH);



    public static void main(String[] args) {

        File[] IGC_files = dirWithIGCLogFiles.listFiles();

        FileDecoder fileDecoder;
        Flight dataLogger;
        IGCDataDAO igc_dao = DaoManager.IGC_SOURCE_DAO;

        int numberOfFilesToLoad = (int) (FileConfig.LOAD_PERCENTAGE * IGC_files.length);
        System.out.println("loading #" + numberOfFilesToLoad + " files");
        int counter = 1;

        for ( int i = 0; i < numberOfFilesToLoad; i++) {
            fileDecoder = new FileDecoder(IGC_files[i].getAbsolutePath());
            dataLogger = fileDecoder.readIGCFile();
            igc_dao.insertDataLogger(dataLogger);
            System.out.println("finished loading file #" + counter++);
        }

    }
}
