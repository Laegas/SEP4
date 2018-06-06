package launchable.fileloaders;

/**
 * Created by kenneth on 31/05/2018.
 */
public class MainFileLoader {

    public MainFileLoader() {}

    public void loadAllFiles() {
        IGCFileLoader igcFileLoader = new IGCFileLoader();
        METARFileLoader metarFileLoader = new METARFileLoader();

        igcFileLoader.loadIGCFiles();
        metarFileLoader.loadMETARFiles();
    }

    public static void main(String[] args) {
        MainFileLoader mainFileLoader = new MainFileLoader();
        mainFileLoader.loadAllFiles();
    }
}
