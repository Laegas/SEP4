package launchable.fileloaders;

import config.FileConfig;
import database.DAO.DaoManager;
import database.DAO.METARSourceDAO;
import model.weather.Airport;
import util.file.FileDecoder;
import model.weather.WeatherRecord;
import util.file.METARTXTFilePreprocessor;

import java.io.File;

public class METARFileLoader {
    private static final File dirWithMETARLogFiles = new File(FileConfig.METAR_DIRECTORY_PATH);

    public static void main(String[] args) {

        File[] METAR_files = dirWithMETARLogFiles.listFiles();

        FileDecoder fileDecoder;
        METARTXTFilePreprocessor preprocessor;
        Airport airport;
        METARSourceDAO METARDAO = DaoManager.METAR_SOURCE_DAO;

        int counter = 1;
        for (File file : METAR_files) {
            preprocessor = new METARTXTFilePreprocessor(file.getAbsolutePath());
            preprocessor.preProcess();
            System.out.println("finished preprocessing file #" + counter + " " + file.getName());
            fileDecoder = new FileDecoder(file.getAbsolutePath());
            airport = fileDecoder.readMETARFile();
            WeatherRecord[] records = airport.getWeatherRecords();
            METARDAO.insertAirport(airport);
            for(WeatherRecord r : records)
                METARDAO.insertWeatherRecord(r);
            System.out.println("finished loading file #" + counter++);
        }
    }
}
