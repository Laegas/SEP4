package launchable;

import config.FileConfig;
import database.DAO.DAOManager;
import fileUtil.FileDecoder;
import model.igc.DataLogger;

public class TestLoadToSourceDB {
    public static void main(String args[])
    {
        FileDecoder file = new FileDecoder(FileConfig.PROJECT_PATH + FileConfig.IGC_FILE);
        DataLogger logger = file.readFile();
        DAOManager.IGC_DAO.insertDataLogger(logger);
    }
}
