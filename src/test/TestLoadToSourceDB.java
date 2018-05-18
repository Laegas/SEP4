package test;

import config.FileConfig;
import database.DAO.IGCdao;
import fileUtil.FileDecoder;
import model.igc.DataLogger;

public class TestLoadToSourceDB {
    public static void main(String args[])
    {
        FileDecoder file = new FileDecoder(FileConfig.PROJECT_PATH + FileConfig.IGC_FILE);
        DataLogger logger = file.readFile();

        IGCdao dao = new IGCdao();
        dao.insertDataLogger(logger);
    }
}
