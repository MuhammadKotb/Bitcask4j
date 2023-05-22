package caskj;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Keydir4j implements Keydir {

    private static class entry {
        public entry(int fileId, int valSize, long valPos, long tstamp) {
            this.fileId = fileId;
            this.valSize = valSize;
            this.valPos = valPos;
            this.tstamp = tstamp;
        }
        public int fileId;
        public int valSize;
        public long valPos;
        public long tstamp;
        

        @Override
        public String toString() {
            return this.fileId + " " + this.valSize + " " + this.valPos + " " + this.tstamp;
        }
    }  

    private Map<Integer, entry> map;

    public Keydir4j() {
        this.map = new HashMap<>();
    }
    @Override
    public int getFileId(int key) {
        return map.get(key).fileId;
    }

    @Override
    public int getValSize(int key) {
        return map.get(key).valSize;

    }

    @Override
    public long getValPos(int key) {
        return map.get(key).valPos;
    }

    @Override
    public long getTimestamp(int key) {
        return map.get(key).tstamp;
    }
    @Override
    public void put(int key, int fileId, int valSize, long valPos, long tstamp) {
        map.put(key, new entry(fileId, valSize, valPos, tstamp));
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
    
}
