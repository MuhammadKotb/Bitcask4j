package caskj;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StatusReader {
    

    protected static Status readStatus(FileInputStream fis, long offset, int len) throws IOException {
        byte[] bytes = new byte[len];
        fis.skip(offset);
        fis.read(bytes);
        return statusFromBytes(bytes);
    }

    protected static Status statusFromBytes(byte[] bytes) {
        byte[] stationIdBytes = Arrays.copyOfRange(bytes, 0, 4);
        byte[] statusNoBytes = Arrays.copyOfRange(bytes, 4, 8);
        byte[] stringLenBytes = Arrays.copyOfRange(bytes, 8, 12);
        int stringLen = ByteBuffer.wrap(stringLenBytes).getInt();
        byte[] batteryStatusBytes = Arrays.copyOfRange(bytes, 12, 12 + stringLen);
        byte[] tstampBytes = Arrays.copyOfRange(bytes, 12 + stringLen, 20 + stringLen);
        byte[] weather0Bytes = Arrays.copyOfRange(bytes, 20 + stringLen, 24 + stringLen);
        byte[] weather1Bytes = Arrays.copyOfRange(bytes, 24 + stringLen, 28 + stringLen);
        byte[] weather2Bytes = Arrays.copyOfRange(bytes, 28 + stringLen, 32 + stringLen);


        int stationId = ByteBuffer.wrap(stationIdBytes).getInt();
        int statusNo = ByteBuffer.wrap(statusNoBytes).getInt();
        String batteryStatus = new String(batteryStatusBytes, StandardCharsets.UTF_8);
        long tstamp = ByteBuffer.wrap(tstampBytes).getLong();
        int[] weather = new int[3];
        weather[0] = ByteBuffer.wrap(weather0Bytes).getInt();
        weather[1] = ByteBuffer.wrap(weather1Bytes).getInt(); 
        weather[2] = ByteBuffer.wrap(weather2Bytes).getInt(); 

        return new Status(stationId, statusNo, batteryStatus, tstamp, weather);

    }
 }
