package caskj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

class Merger implements Runnable {

    private Keydir keydir;
    private File dir;
    private Comparator<File> fileComparator;
    private int currentFile;
    private MergeControl mergeControl;
    public Merger(File dir, Keydir keydir, Comparator<File> fileComparator, int currentFile, MergeControl mergeControl) {
        this.keydir = keydir;
        this.dir = dir;
        this.fileComparator = fileComparator;
        this.currentFile = currentFile;
        this.mergeControl = mergeControl;
    }


    @Override
    public void run() {
        this.mergeControl.setMerging(true);
        System.out.println("Started Merging");
        try {
            Map<Integer, Hint> oldMap = createKeydirMap();
            Map<Integer, Status> keyValMap = readReplFiles(oldMap);
            deleteDataFiles();
            FileOutputStream fos = new FileOutputStream(createDataFile(), true);
            Map<Integer, Hint> newMap = writeDataFile(fos, oldMap, keyValMap);
            fos.flush();
            fos.close();
            deleteHintFiles();
            fos = new FileOutputStream(createHintFile(), true);
            writeHintFile(fos, newMap);
            fos.flush();
            fos.close();
            updateKeydir(newMap);
            this.mergeControl.setMerging(false);
            deleteReplFiles();
            fos = new FileOutputStream(createReplFile(), true);
            writeDataFile(fos, oldMap, keyValMap);

        } catch(Exception e) {
            System.out.println("Could not merge");
            e.printStackTrace();
        }
        this.mergeControl.setMerging(false);
        System.out.println("Ended Merging");
    }

    private Map<Integer, Hint> createKeydirMap() throws IOException {
        Map<Integer, Hint> map = new HashMap<>();

        File[] hintFiles = this.dir.listFiles((f -> f.getPath().contains("hint")));
        Arrays.sort(hintFiles, this.fileComparator);
        hintFiles = Arrays.copyOfRange(hintFiles, 0, 5);
        
        for(int i = 0; i < hintFiles.length; i++) {
            FileInputStream fis = new FileInputStream(hintFiles[i]);
            Map<Integer, Hint> tempMap = HintReader.readHints(fis);
            for(Integer k : tempMap.keySet()) {
                map.put(k, tempMap.get(k));
            } 
            fis.close();
        }
       
        return map;
    }

    private Map<Integer, Status> readReplFiles(Map<Integer, Hint> map) throws IOException{
        Map<Integer, Status> keyValMap = new HashMap<>();
        File[] replFiles = this.dir.listFiles((f -> f.getPath().contains("repl")));
        Arrays.sort(replFiles, this.fileComparator);
        replFiles = Arrays.copyOfRange(replFiles, 0, 5);
        for(Integer k : map.keySet()) {
            Hint h = map.get(k);
            for(int i = 0; i < replFiles.length; i++) {
                if(Integer.parseInt(replFiles[i].getName().substring(4)) == h.fileId) {
                    FileInputStream fis = new FileInputStream(replFiles[i]);
                    Status status = StatusReader.readStatus(fis, h.valPos, h.valSize);
                    keyValMap.put(k, status);
                    fis.close();
                    break;
                }
            }
        }
        return keyValMap;
    }
    
    private Map<Integer, Hint> writeDataFile(FileOutputStream fos, Map<Integer, Hint> oldMap, Map<Integer, Status> keyValMap) throws IOException{
        Map<Integer, Hint> newMap = new HashMap<>();
        long offset = 0;
        for(Integer k : keyValMap.keySet()) {
            Hint hint = oldMap.get(k);
            offset += DataWriter.writeTstamp(fos, hint.tstamp);
            offset += DataWriter.writeKeySize(fos);
            offset += DataWriter.writeKey(fos, k);
            offset += DataWriter.writeValSize(fos, keyValMap.get(k));
            long temp_offset = offset;
            offset += DataWriter.writeVal(fos, keyValMap.get(k));
            newMap.put(k, new Hint(this.currentFile - 1, StatusUtil.getStatusSize(keyValMap.get(k)), temp_offset, hint.tstamp));
        } 
        return newMap;
    }

    private void writeHintFile(FileOutputStream fos, Map<Integer, Hint> map) throws IOException{
        for(Integer k : map.keySet()) {
            HintWriter.writeHint(fos, map.get(k), k);
        }

    }
    private File createDataFile() {
        return new File(this.dir + "/data" + (this.currentFile - 1));
    }
    private File createReplFile() {
        return new File(this.dir + "/repl" + (this.currentFile - 1));
    }
    private File createHintFile() {
        return new File(this.dir + "/hint" + (this.currentFile - 1));
    }
    

    private void deleteHintFiles() throws Exception{
        File[] hintFiles = this.dir.listFiles((f -> f.getPath().contains("hint")));
        Arrays.sort(hintFiles, this.fileComparator);
        hintFiles = Arrays.copyOfRange(hintFiles, 0, 5);

        for(File f : hintFiles) {
            if(!f.delete()) throw new Exception("Could not delete a hint file while merging");
        }
    }

    private void deleteDataFiles() throws Exception{
        File[] dataFiles = this.dir.listFiles((f -> f.getPath().contains("data")));
        Arrays.sort(dataFiles, this.fileComparator);
        dataFiles = Arrays.copyOfRange(dataFiles, 0, 5);

        for(File f : dataFiles) {
            if(!f.delete()) throw new Exception("Could not delete a data file while merging");
        }
    }

    private void deleteReplFiles() throws Exception{
        File[] replFiles = this.dir.listFiles((f -> f.getPath().contains("repl")));
        Arrays.sort(replFiles, this.fileComparator);
        replFiles = Arrays.copyOfRange(replFiles, 0, 5);

        for(File f : replFiles) {
            if(!f.delete()) throw new Exception("Could not delete a repl file while merging");
        }
    }

    private void updateKeydir(Map<Integer, Hint> newMap) {
        for(Integer k : newMap.keySet()) {
            Hint hint = newMap.get(k);
            Hint keyDirHint = this.keydir.getHint(k);
            if(hint.tstamp > keyDirHint.tstamp) {
                this.keydir.put(k, hint);
            }
        }
    }
    
}
