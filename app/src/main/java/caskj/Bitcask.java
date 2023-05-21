package caskj;

import java.io.File;
import java.util.List;

public interface Bitcask {
    BitcaskHandle open(File dir);
    int get(BitcaskHandle bitcaskHandle, int key);
    void put(BitcaskHandle buBitCaskHandle, int key, int val);
    List<Integer> listKeys(BitcaskHandle bitCaskHandle);
    void merge(File dir);
    void sync(BitcaskHandle bitCaskHandle);
    void close(BitcaskHandle bitCaskHandle);
}
