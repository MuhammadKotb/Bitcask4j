package caskj;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class DataWriter {

    // protected static long writeData(FileOutputStream fos, int key, Status val) {
    //     long offset = 0;

    //     try {

    //     }
    //     offset += writeTstamp(fos);
    //     offset += DataWriter.writeKeySize(fos);
    //     offset += DataWriter.writeKey(fos, key);
    //     offset += DataWriter.writeValSize(fos, val);
    //     long offset_temp = offset;
    //     offset += DataWriter.writeVal(fos, val);

    // }
    protected static int writeKeySize(FileOutputStream fos) throws IOException {
        byte[] bytes = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(4).array();
        fos.write(bytes);
        return bytes.length;
    }
    protected static int writeKey(FileOutputStream fos, int key) throws IOException {
        byte[] bytes = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(key).array();
        fos.write(bytes);
        return bytes.length;
    }
    protected static int writeValSize(FileOutputStream fos, Status val) throws IOException {
        int bytesSize = StatusUtil.getStatusSize(val);
        byte[] bytes = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(bytesSize).array();
        fos.write(bytes);
        return bytes.length;
    }
    protected static int writeVal(FileOutputStream fos, Status val) throws IOException {
        int bytes = StatusWriter.write(fos, val);
        return bytes;
    }
    protected static long[] writeTstamp(FileOutputStream fileOut) throws IOException {
        long tstamp = System.currentTimeMillis();
        byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(tstamp).array();
        fileOut.write(bytes);
        return new long[]{tstamp, bytes.length};
    }
    protected static int writeTstamp(FileOutputStream fileOut, long tstamp) throws IOException {
        byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(tstamp).array();
        fileOut.write(bytes);
        return bytes.length;
    }
}
