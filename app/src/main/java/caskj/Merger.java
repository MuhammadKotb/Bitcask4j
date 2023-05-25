package caskj;

import java.io.File;
import java.util.Map;
import java.util.Timer;

class Merger implements Runnable {

    private Keydir keydir;
    private File dir;
    public Merger(File dir, Keydir keydir) {
        this.keydir = keydir;
        this.dir = dir;
    }
    @Override
    public void run() {
        System.out.println("Merg");
    }

    private void createKeydirMap(int[] fileIds) {

    }

    private void deleteHintFiles(int[] fileIds) {

    }

    private void createHintFiles(Map<Integer, Hint> keydirMap) {


    }



    
}
