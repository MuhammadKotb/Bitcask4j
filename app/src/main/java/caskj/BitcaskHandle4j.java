package caskj;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;


public class BitcaskHandle4j implements BitcaskHandle {


    final private long maxFileSize_mb = 100;
    private Keydir keydir;
    private File directory; 
    private File activeFile;
    private int currentFile = 0;   
    private long offset = 0;

    public BitcaskHandle4j(File dir) {
        directory = dir;
    }
    public BitcaskHandle4j(String dirPath) {
        directory = new File(dirPath);
    }

    @Override
    public <T extends Serializable, K extends Serializable> void append(T key, K val) throws IOException {
        FileOutputStream fos = new FileOutputStream(this.activeFile);
        writeTstamp(fos);
        writeObjectSize(fos, key);
        writeObjectSize(fos, val);
        writeObject(fos, key);
        writeObject(fos, val);
    }

    @Override
    public void merge() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'merge'");
    }

    @Override
    public <T extends Serializable> void get(T key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    private <T extends Serializable> void writeObject(FileOutputStream fileOut, T obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        out.flush();
        byte[] bytes = bos.toByteArray();
        bos.close();
        out.close();
        fileOut.write(bytes);
        fileOut.flush();
    }

    private <T extends Serializable> void writeObjectSize(FileOutputStream fileOut, T obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        out.flush();
        byte[] bytes = bos.toByteArray();
        bos.close();
        out.close();   
        int size = bytes.length;
        fileOut.write(size);
        fileOut.flush();

    }
    private void writeTstamp(FileOutputStream fileOut) throws IOException {
        byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(System.currentTimeMillis()).array();
        fileOut.write(bytes);
        fileOut.flush();
    }

    
}
