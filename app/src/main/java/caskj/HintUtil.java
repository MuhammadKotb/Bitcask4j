package caskj;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class HintUtil {
    
    protected static byte[] getHintBytes(Hint hint) {
        byte[] bytes = new byte[24];
        int ctr = 0;
        byte[] fileIdBytes = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(hint.fileId).array();
        for(int i = 0; i < fileIdBytes.length; i++) {
            bytes[ctr++] = fileIdBytes[i];
        }
        byte[] valSizeBytes = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(hint.valSize).array();    
        for(int i = 0; i < valSizeBytes.length; i++) {
            bytes[ctr++] = valSizeBytes[i];
        }
        byte[] valPosBytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(hint.valPos).array();    
        for(int i = 0; i < valPosBytes.length; i++) {
            bytes[ctr++] = valPosBytes[i];
        }
        byte[] tstampBytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(hint.tstamp).array();
        for(int i = 0; i < tstampBytes.length; i++) {
            bytes[ctr++] = tstampBytes[i];
        }
        return bytes;
    }
    
    protected static List<Byte[]> readHintsBytes(FileInputStream fis) throws IOException {
        List<Byte[]> bytes = new ArrayList<Byte[]>();
        byte[] bytesRead;
        while((bytesRead = fis.readNBytes(28)).length == 28) {
            Byte[] bytesReadObj = new Byte[bytesRead.length];
            for(int i = 0; i < bytesRead.length; i++) {
                bytesReadObj[i] = bytesRead[i];
            }
            bytes.add(bytesReadObj);
        }
        return bytes;
    } 

}
