package caskj;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Keydir4j implements Keydir {



    private Map<Integer, Hint> map;

    public Keydir4j() {
        this.map = new ConcurrentHashMap<>();
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
    public Hint getHint(int key) {
        return this.map.get(key);
    }

    @Override
    public String toString() {
        return this.map.toString();
    }

    @Override
    public void addHints(Map<Integer, Hint> hints) {
        for(int k : hints.keySet()) {
            this.map.put(k, hints.get(k));
        }
    }

    
}
