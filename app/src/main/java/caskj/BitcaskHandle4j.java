package caskj;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class BitcaskHandle4j implements BitcaskHandle {


    final private long maxFileSize_kb = 100;
    final private long hintMaxFileSize_kb = 10;
    private Keydir keydir;
    private File directory; 
    private File activeFile = null;
    private File activeHintFile = null;
    public int currentFile = 0;   
    public long offset = 0;
    private FileOutputStream bitcaskDataWriter = null;
    private FileOutputStream bitcaskHintWriter = null;


    public BitcaskHandle4j(File dir) {
        directory = dir;
        this.init();
        this.keydir = new Keydir4j();
    }


    @Override
    public int getCurrentFile() {
        return this.currentFile;
    }

    @Override
    public long getOffset() {
        return this.offset;
    }

    @Override
    public void append(int key, Status val) throws IOException {
        try {
            checkActiveFile();
        } catch(Exception e) {
            System.out.println("Could Check Active file");
            e.printStackTrace();
            return;
        }

        long offset_temp = offset;

        offset += writeTstamp(this.bitcaskDataWriter);
        offset += writeKeySize(this.bitcaskDataWriter);
        offset += writeKey(this.bitcaskDataWriter, key);

        int valSizetmp = writeValSize(this.bitcaskDataWriter, val); 
        offset += valSizetmp;
        offset += writeVal(this.bitcaskDataWriter, val);
        
        this.bitcaskDataWriter.flush();


        Hint hint = new Hint(currentFile, valSizetmp, offset_temp, val.timestamp);

        try {
            HintWriter.writeHint(bitcaskHintWriter, hint, key);
            this.bitcaskHintWriter.flush();
        } catch(Exception e) {
            System.out.println("Could not write hint file");
            e.printStackTrace();
        }

        keydir.put(key, hint);
    }

    @Override
    public void merge() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'merge'");
    }
    
    @Override
    public Status get(int key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
    

    private int writeKeySize(FileOutputStream fos) throws IOException {
        byte[] bytes = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(4).array();
        fos.write(bytes);
        return bytes.length;
    }
    private int writeKey(FileOutputStream fos, int key) throws IOException {
        byte[] bytes = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(key).array();
        fos.write(bytes);
        return bytes.length;
    }
    private int writeValSize(FileOutputStream fos, Status val) throws IOException {
        int bytesSize = StatusUtil.getStatusSize(val);
        byte[] bytes = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(bytesSize).array();
        fos.write(bytes);
        return bytes.length;
    }
    private int writeVal(FileOutputStream fos, Status val) throws IOException {
        int bytes = StatusWriter.write(fos, val);
        return bytes;
    }
    private int writeTstamp(FileOutputStream fileOut) throws IOException {
        byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(System.currentTimeMillis()).array();
        fileOut.write(bytes);
        return bytes.length;
    }

    private File createDataFile() {
        return new File(directory.getPath() + "/data" + currentFile);
    }

    private File createHintFile() {
        return new File(directory.getPath() + "/hint" + currentFile);
    }

    private long getFileSize(File file) {
        return file.length() / (1024);
    }


    private void init() {
        File[] dataFiles = this.directory.listFiles((f -> f.getPath().contains("data")));
        File[] hintFiles = this.directory.listFiles((f -> f.getPath().contains("hint")));
        
        if(dataFiles.length == 0) {
            this.offset = 0;
            this.currentFile = 0;
        }
        else {
            Arrays.sort(dataFiles);
            Arrays.sort(hintFiles);
            this.activeFile = dataFiles[dataFiles.length - 1];
            this.activeHintFile = hintFiles[hintFiles.length - 1];
            this.currentFile = (dataFiles.length) - 1;
            this.offset = dataFiles[dataFiles.length - 1].length();

        }
    }

    private void checkActiveFile() throws IOException {
        if(currentFile == 0 && this.activeFile == null) {
            System.out.println("Created New");
            this.activeFile = createDataFile();
            this.activeHintFile = createHintFile();
            this.bitcaskDataWriter = new FileOutputStream(this.activeFile, true);
            this.bitcaskHintWriter = new FileOutputStream(this.activeHintFile, true);
        }
        else if(currentFile == 0 && this.activeFile != null) {
            this.bitcaskDataWriter = new FileOutputStream(this.activeFile, true);
            this.bitcaskHintWriter = new FileOutputStream(this.activeHintFile, true);
        }
        else if(currentFile > 0 && this.activeFile != null) {
            long size = this.getFileSize(activeFile);
            if(size >= this.maxFileSize_kb) {
                System.out.println("Created New overflow");
                this.activeFile = createDataFile();
                this.activeHintFile = createHintFile();
                currentFile++;
                offset = 0;
                this.bitcaskDataWriter.flush();
                this.bitcaskDataWriter.close();
                this.bitcaskHintWriter.flush();
                this.bitcaskHintWriter.close();
                this.bitcaskDataWriter = new FileOutputStream(this.activeFile, true);
                this.bitcaskHintWriter = new FileOutputStream(this.activeHintFile, true);
            }
        }
    }


    
}
