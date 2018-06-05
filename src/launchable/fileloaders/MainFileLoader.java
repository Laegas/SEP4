package launchable.fileloaders;

/**
 * Created by kenneth on 31/05/2018.
 */
public class MainFileLoader {
    public static void main(String[] args) {
       Thread t1 =  new Thread(() -> IGCFileLoader.main(new String[1]));
        Thread t2 = new Thread(() -> METARFileLoader.main(new String[1]));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
