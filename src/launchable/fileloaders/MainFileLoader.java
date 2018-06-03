package launchable.fileloaders;

/**
 * Created by kenneth on 31/05/2018.
 */
public class MainFileLoader {
    public static void main(String[] args) {
        new Thread(() -> IGCFileLoader.main(new String[1])).start();
        new Thread(() -> METARFileLoader.main(new String[1])).start();
    }
}
