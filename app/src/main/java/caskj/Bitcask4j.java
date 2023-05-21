package caskj;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class Bitcask4j implements Bitcask {

    @Override
    public BitcaskHandle open(File dir) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'open'");
    }

    @Override
    public <T extends Serializable> int get(BitcaskHandle bitcaskHandle, T key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public <T extends Serializable, K extends Serializable> void put(BitcaskHandle bitCaskHandle, T key, K val) {
        try {
            bitCaskHandle.append(key, val);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> listKeys(BitcaskHandle bitCaskHandle) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listKeys'");
    }

    @Override
    public void merge(File dir) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'merge'");
    }

    @Override
    public void sync(BitcaskHandle bitCaskHandle) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sync'");
    }

    @Override
    public void close(BitcaskHandle bitCaskHandle) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }

  
    





}
