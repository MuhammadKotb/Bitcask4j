package caskj;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HintReader {

    protected static Map<Integer, Hint> readHints(FileInputStream fis) throws IOException {
        List<Byte[]> hintsBytes = HintUtil.readHintsBytes(fis); 
        Map<Integer, Hint> map = new HashMap<>();

        for(int i = 0; i < hintsBytes.size(); i++) {

            Byte[] bytesObj = hintsBytes.get(i);

            byte[] bytes = new byte[bytesObj.length];

            for(int k = 0; k < bytes.length; k++) {
                bytes[k] = bytesObj[k];
            }

            byte[] keyBytes = Arrays.copyOfRange(bytes, 0, 4); // int bytes -> 4
            byte[] fileIdBytes = Arrays.copyOfRange(bytes, 4, 8); // int bytes -> 4
            byte[] valSizeBytes = Arrays.copyOfRange(bytes, 8, 12); // int bytes -> 4
            byte[] valPosBytes = Arrays.copyOfRange(bytes, 12, 20); // long bytes -> 8
            byte[] tstampBytes = Arrays.copyOfRange(bytes, 20, 28); // long bytes -> 8
            
            int key = ByteBuffer.wrap(keyBytes).getInt();
            int fileId = ByteBuffer.wrap(fileIdBytes).getInt();
            int valSize = ByteBuffer.wrap(valSizeBytes).getInt();
            long valPos = ByteBuffer.wrap(valPosBytes).getLong();
            long tstamp = ByteBuffer.wrap(tstampBytes).getLong();

            map.put(key, new Hint(fileId, valSize, valPos, tstamp));
        }

        return map;
    }
    
}
