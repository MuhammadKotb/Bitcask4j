package caskj;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Keydir4j implements Keydir {



    private Map<Integer, Hint> map;

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
    public void put(int key, Hint hint) {
        map.put(key, hint);
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
    
}
