package launchable;

import database.DAO.DAOManager;
import database.DAO.IGCDataDAO;
import fileUtil.FileDecoder;
import model.igc.DataLogger;

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
        DataLogger dataLogger;
        IGCDataDAO igc_dao = DAOManager.IGC_DAO;
        if(IGC_files.length > 0)
        for (File file : IGC_files) {
            fileDecoder = new FileDecoder(file.getAbsolutePath());
            dataLogger = fileDecoder.readFile();

            igc_dao.insertDataLogger(dataLogger);

        }

    }
}
