package util.file;

import database.DAO.DaoManager;
import database.DAO.IGCDataDAO;
import model.igc.Flight;

public class FileRunner implements Runnable {
    private FileQueue fileQueue;


    public FileRunner(FileQueue fileQueue)
    {
        this.fileQueue = fileQueue;
    }
    @Override
    public void run() {
        FileDecoder fd = new FileDecoder();
        String fn;
        IGCDataDAO igc_dao = DaoManager.IGC_SOURCE_DAO;
        Flight flight = null;
        while((fn=fileQueue.getNextFileName())!=null)
        {
            String[] info = fn.split("\\?");
            System.out.println("loading file #" + info[1]);
            fd.setFile(info[0]);
            flight =fd.readIGCFile();
            igc_dao.insertDataLogger(flight);
            System.out.println("Finished loading file #" + info[1]);
        }
    }
}
