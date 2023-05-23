package caskj;

import java.nio.ByteBuffer;
import java.util.Arrays;
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
    

}
