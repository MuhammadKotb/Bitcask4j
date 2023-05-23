package caskj;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bitcask4j implements Bitcask {


    private Map<File, BitcaskHandle> file2handle;

    public Bitcask4j() {
        file2handle = new HashMap<>();
    }
    @Override
    public BitcaskHandle open(File dir) throws Exception {
        dir.mkdir();
        if(file2handle.containsKey(dir)) return file2handle.get(dir);
        else {
            BitcaskHandle handle = new BitcaskHandle4j(dir);
            file2handle.put(dir, handle);
            return handle;
        }
    }


    @Override
    public Status get(BitcaskHandle bitcaskHandle, int key) {
        return bitcaskHandle.get(key);
    }

    @Override
    public void put(BitcaskHandle bitCaskHandle, int key, Status val) {
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
