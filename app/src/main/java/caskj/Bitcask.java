package caskj;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public interface Bitcask {
    BitcaskHandle open(File dir) throws Exception;
    Status get(BitcaskHandle bitcaskHandle, int key);
    void put(BitcaskHandle bitCaskHandle, int key, Status val);
    List<Integer> listKeys(BitcaskHandle bitCaskHandle);
    void merge(File dir);
    void sync(BitcaskHandle bitCaskHandle);
    void close(BitcaskHandle bitCaskHandle);
}
