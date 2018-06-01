package util.file;

import java.util.List;

public class FileQueue {
    private List<String> fileNameQueue;

    public FileQueue(List<String> fileNames) {
        this.fileNameQueue = fileNames;
    }

    public synchronized String getNextFileName()
    {
        if(fileNameQueue.size()==0)
            return null;
        return fileNameQueue.remove(fileNameQueue.size()-1)+"?"+(40-fileNameQueue.size());
    }
}
