package launchable.fileloaders;

import config.FileConfig;
import util.file.FileQueue;
import util.file.FileRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IGCFileLoader {

    private static final File dirWithIGCLogFiles = new File(FileConfig.IGC_DIRECTORY_PATH);

    public IGCFileLoader() {}

    public void loadIGCFiles() {
        System.out.println("Setup of file queue.");
        File[] IGC_files = dirWithIGCLogFiles.listFiles();
        List<String> fileNames = new ArrayList<>();
        int numberOfFilesToLoad = (int) (FileConfig.LOAD_PERCENTAGE * IGC_files.length);
        for(int i = 0;i<numberOfFilesToLoad;i++)
        {
            fileNames.add(IGC_files[i].getAbsolutePath());
        }
        FileQueue queue = new FileQueue(fileNames);
        System.out.println("Setup Finished, starting threads...");
        for(int i = 0;i<numberOfFilesToLoad/3+1;i++)
        {
            Thread a = new Thread(new FileRunner(queue));
            a.start();
            System.out.print(".");
            try {
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Threads started, loading "+ numberOfFilesToLoad+" files.");
    }
}
