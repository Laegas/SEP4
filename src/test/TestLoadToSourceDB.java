package test;

import database.DAO.IGCdao;
import file.FileDecoder;
import model.DataLogger;

public class TestLoadToSourceDB {
    public static void main(String args[])
    {
        FileDecoder file = new FileDecoder(FileDecoder.FILENAME_DIRECTORY);
        DataLogger logger = file.readFile();

        IGCdao dao = new IGCdao();
        dao.insertDataLogger(logger);
    }
}
