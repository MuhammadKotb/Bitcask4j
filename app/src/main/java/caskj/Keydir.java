package caskj;

import java.io.File;

public interface Keydir {
    void put(int key, int fileId, int valSize, long valPos, long tstamp);
    int getFileId(int key);
    int getValSize(int key);
    long getValPos(int key);
    long getTimestamp(int key);
}