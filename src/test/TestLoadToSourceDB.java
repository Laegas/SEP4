package test;

import config.FileConfig;
import database.DAO.DaoManager;
import util.file.FileDecoder;
import model.igc.Flight;

public class TestLoadToSourceDB {
    public static void main(String args[])
    {
        FileDecoder file = new FileDecoder(FileConfig.PROJECT_PATH + FileConfig.IGC_FILE);
        Flight logger = file.readIGCFile();

        DaoManager.IGC_SOURCE_DAO.insertDataLogger(logger);
    }
}
