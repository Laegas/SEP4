package test;

import config.FileConfig;
import database.DAO.DaoManager;
import fileUtil.FileDecoder;
import model.igc.Flight;

public class TestLoadToSourceDB {
    public static void main(String args[])
    {
        FileDecoder file = new FileDecoder(FileConfig.PROJECT_PATH + FileConfig.IGC_FILE);
        Flight logger = file.readFile();

        DaoManager.IGC_DAO.insertDataLogger(logger);
    }
}
