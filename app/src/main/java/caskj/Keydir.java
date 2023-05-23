package caskj;

import java.io.File;

public interface Keydir {
    void put(int key, Hint hint);
    int getFileId(int key);
    int getValSize(int key);
    long getValPos(int key);
    long getTimestamp(int key);
}