package caskj;

import java.io.IOException;
import java.io.Serializable;

public interface BitcaskHandle {
    void append(int key, Status val) throws IOException ;
    void merge();
    Status get(int key);
    int getCurrentFile();
    long getOffset();
    Keydir getKeydir();
    void destroy() throws IOException;
}