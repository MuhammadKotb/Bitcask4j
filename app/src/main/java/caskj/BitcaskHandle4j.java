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


    final private long maxFileSize_mb = 10;
    private Keydir keydir;
    private File directory; 
    private File activeFile = null;
    public int currentFile = 0;   
    public int offset = 0;
    private FileOutputStream bitcaskWriter = null;
    private FileOutputStream bitcaskDataWriter = null;
    private File bitcaskFile = null;

    public BitcaskHandle4j(File dir) {
        directory = dir;
        this.init();
    }


    @Override
    public int getCurrentFile() {
        return this.currentFile;
    }

    @Override
    public int getOffset() {
        return this.offset;
    }

    @Override
    public <T extends Serializable, K extends Serializable> void append(T key, K val) throws IOException {

        if(currentFile == 0 || this.activeFile == null) {
            System.out.println("Created New");
            this.activeFile = createDataFile();
            currentFile++;
            this.bitcaskDataWriter = new FileOutputStream(this.activeFile, true);
        }
        else if(currentFile > 0 && this.activeFile != null) {
            long size = this.getFileSize(activeFile);
            if(size >= this.maxFileSize_mb) {
                System.out.println("Created New overflow");
                this.activeFile = createDataFile();
                currentFile++;
                offset = 0;
                this.bitcaskDataWriter.flush();
                this.bitcaskDataWriter.close();
                this.bitcaskDataWriter = new FileOutputStream(this.activeFile, true);
            }
        }
        offset += writeTstamp(this.bitcaskDataWriter);
        offset += writeObjectSize(this.bitcaskDataWriter, key);
        offset += writeObjectSize(this.bitcaskDataWriter, val);
        offset += writeObject(this.bitcaskDataWriter, key);
        offset += writeObject(this.bitcaskDataWriter, val);
        this.bitcaskDataWriter.flush();


        try {
            this.updateBitcaskFile(this.bitcaskWriter);
            this.bitcaskWriter.flush();
        } catch(Exception e){
            System.out.println("Could not Update Bitcask file");
            e.printStackTrace();
        }
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
    
    private <T extends Serializable> int writeObject(FileOutputStream fileOut, T obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        out.flush();
        byte[] bytes = bos.toByteArray();
        bos.close();
        out.close();
        fileOut.write(bytes);
        System.out.println(bytes.length);
        return bytes.length;
    }

    private <T extends Serializable> int writeObjectSize(FileOutputStream fileOut, T obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        out.flush();
        byte[] bytes = bos.toByteArray();
        bos.close();
        out.close();   
        int size = bytes.length;
        byte[] bytesSize = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(size).array(); 
        fileOut.write(bytesSize);
        System.out.println(bytesSize.length);
        return bytesSize.length;
    }
    private long writeTstamp(FileOutputStream fileOut) throws IOException {
        byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(System.currentTimeMillis()).array();
        fileOut.write(bytes);
        System.out.println(bytes.length);
        return bytes.length;
    }

    private File createDataFile() {
        return new File(directory.getPath() + "/data" + currentFile);
    }

    private long getFileSize(File file) {
        return file.length() / (1024 * 1024);
    }

    private File createBitcaskFile() {
        return new File(directory.getPath() + "/bitcask.txt");
    } 

    private void init() {
        List<File> files = Arrays.asList(this.directory.listFiles((f -> f.getName().equals("bitcask.txt"))));
        if(files.size() == 0) {
            try {
                this.bitcaskFile = createBitcaskFile();
                this.bitcaskWriter = new FileOutputStream(this.bitcaskFile, false);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                this.bitcaskFile = files.get(0);
                FileInputStream fis = new FileInputStream(this.bitcaskFile);
                readBitcaskFile(fis);
                fis.close();
                this.bitcaskWriter = new FileOutputStream(this.bitcaskFile, false);
                this.activeFile = new File(this.directory.getPath() + "/data" + (this.currentFile - 1));
                this.bitcaskDataWriter = new FileOutputStream(activeFile, true);    
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        
    }

    private void readBitcaskFile(FileInputStream fis) throws IOException{
        byte[] currentFileBytes = fis.readNBytes(4);
        this.currentFile = ByteBuffer.wrap(currentFileBytes).getInt();
        byte[] offsetBytes = fis.readNBytes(4);
        this.offset = ByteBuffer.wrap(offsetBytes).getInt();
    }

    private void updateBitcaskFile(FileOutputStream fos) throws IOException {
        byte[] currentFileBytes = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(this.currentFile).array();
        fos.write(currentFileBytes);
        byte[] offsetBytes = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(this.offset).array();
        fos.write(offsetBytes);
    }

    
}
