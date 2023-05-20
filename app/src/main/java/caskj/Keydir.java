package caskj;

import java.io.File;

public interface Keydir {
    File getFile();
    long getValSize();
    long getValPos();
    long getTimestamp();
}