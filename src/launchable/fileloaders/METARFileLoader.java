package launchable.fileloaders;

import config.FileConfig;
import database.DAO.DaoManager;
import database.DAO.METARSourceDAO;
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
        WeatherRecord[] weatherRecords;
        METARSourceDAO METARDAO = DaoManager.METAR_SOURCE_DAO;

        int counter = 1;
        for (File file : METAR_files) {
            if (counter > 1) break;
            preprocessor = new METARTXTFilePreprocessor(file.getAbsolutePath());
            preprocessor.preProcess();
            System.out.println("finished preprocessing file #" + counter++);
        }

        counter = 1;

        for (File file : METAR_files) {
            if(counter > 1) break;
            fileDecoder = new FileDecoder(file.getAbsolutePath());
            weatherRecords = fileDecoder.readMETARFile();
            /*
            for(WeatherRecord r : weatherRecords)
                METARDAO.insertWeatherRecord(r);
            */
            System.out.println("finished loading file #" + counter++);
        }
    }
}
