package launchable;

import fileUtil.FileDecoder;
import model.DataLogger;

import java.io.File;

/**
 * Created by kenneth on 17/05/2018.
 */
public class IGCFileLoader {

    private static final File dirWithIGCLogFiles = new File("C:\\IGC_files");

    public static void main(String[] args) {

        //getting all files in the directory

        File[] IGC_files = dirWithIGCLogFiles.listFiles();

        FileDecoder fileDecoder;
        DataLogger dataLogger;
        for (File file : IGC_files) {
            fileDecoder = new FileDecoder(file.getAbsolutePath());
            dataLogger = fileDecoder.readFile();


            // TODO send datalogger to the dao

        }

    }
}
