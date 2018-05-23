package launchable;

import config.FileConfig;
import database.DAO.DaoManager;
import database.DAO.IGCDataDAO;
import database.DAO.METARSourceDAO;
import fileUtil.FileDecoder;
import model.igc.Flight;
import model.weather.WeatherRecord;

import java.io.File;

public class METARFileLoader {
    private static final File dirWithMETARLogFiles = new File(FileConfig.METAR_DIRECTORY_PATH);

    public static void main(String[] args) {

        File[] METAR_files = dirWithMETARLogFiles.listFiles();

        FileDecoder fileDecoder;
        WeatherRecord weatherRecord;
        METARSourceDAO METARDAO = DaoManager.METAR_SOURCE_DAO;

        System.out.println("loading #" + METAR_files.length + " files");
        int counter = 1;

        for (File file : METAR_files) {
            if(counter > 1) break;
            fileDecoder = new FileDecoder(file.getAbsolutePath());
            weatherRecord = fileDecoder.readMETARFile();
            //METARDAO.insertWeatherRecord(weatherRecord);
            System.out.println("finished loading file #" + counter++);
        }
    }
}
