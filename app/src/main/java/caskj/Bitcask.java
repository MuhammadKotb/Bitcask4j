package caskj;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public interface Bitcask {
    BitcaskHandle open(File dir) throws Exception;
    <T extends Serializable> int get(BitcaskHandle bitcaskHandle, T key);
    <T extends Serializable, K extends Serializable> void put(BitcaskHandle bitCaskHandle, T key, K val);
    List<Integer> listKeys(BitcaskHandle bitCaskHandle);
    void merge(File dir);
    void sync(BitcaskHandle bitCaskHandle);
    void close(BitcaskHandle bitCaskHandle);
}
