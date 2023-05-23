package caskj;

import java.io.File;
import java.util.Map;

public interface Keydir {

    void put(int key, Hint hint);
    Hint getHint(int key);
    int getFileId(int key);
    int getValSize(int key);
    long getValPos(int key);
    long getTimestamp(int key);
    void addHints(Map<Integer, Hint> hints);
}