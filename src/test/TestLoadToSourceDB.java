package test;

import database.DAO.DAO;
import file.FileDecoder;
import model.DataLogger;

public class TestLoadToSourceDB {
    public static void main(String args[])
    {
        FileDecoder file = new FileDecoder(FileDecoder.FILENAME_DIRECTORY);
        DataLogger logger = file.readFile();

        DAO dao = new DAO();
        dao.insertDataLogger(logger);
    }
}
