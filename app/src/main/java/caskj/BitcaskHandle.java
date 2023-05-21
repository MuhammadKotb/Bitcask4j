package caskj;

import java.io.IOException;
import java.io.Serializable;

public interface BitcaskHandle {
    <T extends Serializable, K extends Serializable> void append(T key, K val) throws IOException ;
    void merge();
    <T extends Serializable> void get(T key);

    int getCurrentFile();
    int getOffset();
}