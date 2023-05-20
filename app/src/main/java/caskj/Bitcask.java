package caskj;

import java.io.File;
import java.util.List;

public interface Bitcask {
    BitCaskHandle open(File dir);
    int get(BitCaskHandle bitcaskHandle, int key);
    void put(BitCaskHandle buBitCaskHandle, int key, int val);
    List<Integer> listKeys(BitCaskHandle bitCaskHandle);
    void merge(File dir);
    void sync(BitCaskHandle bitCaskHandle);
    void close(BitCaskHandle bitCaskHandle);
}
