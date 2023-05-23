package caskj;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class HintWriter {

    public static void writeHint(FileOutputStream fos, Hint hint, int key) throws IOException {


        byte[] hintBytes = HintUtil.getHintBytes(hint);
        byte[] keyBytes = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(key).array();
        byte[] bytes = new byte[hintBytes.length + keyBytes.length];
        
        int ctr = 0;

        for(int i = 0; i < keyBytes.length; i++) {
            bytes[ctr++] = keyBytes[i];
        }
        for(int i = 0; i < hintBytes.length; i++) {
            bytes[ctr++] = hintBytes[i];
        }

        fos.write(bytes);
    }
    
}
