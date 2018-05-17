package test;

import database.DAO.IGCdao;
import fileUtil.FileDecoder;
import model.igc.DataLogger;

public class TestLoadToSourceDB {
    public static void main(String args[])
    {
        FileDecoder file = new FileDecoder(FileDecoder.FILENAME_DIRECTORY);
        DataLogger logger = file.readFile();

        IGCdao dao = new IGCdao();
        dao.insertDataLogger(logger);
    }
}
