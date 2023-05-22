package caskj;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class StatusUtil {

    public static byte[] getBytes(Status status) {

        byte[] bytes;
        try {
            int StringLen = getStringBytes(status.batteryStatus);
            bytes = new byte[28 + StringLen];
        } catch(Exception e) {
            System.out.println("Could not Get String size");
            e.printStackTrace();  
            return null;
        }
        int ctr = 0;
        byte[] id = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(status.stationId).array();
        for(int i = 0; i < id.length; i++) {
            bytes[ctr++] = id[i];
        } 
        byte[] no = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(status.statusNo).array();
        for(int i = 0; i < no.length; i++) {
            bytes[ctr++] = no[i];
        }
        try {
            byte[] batteryStatus = status.batteryStatus.getBytes("UTF-8");
            for(int i = 0; i < batteryStatus.length; i++) {
                bytes[ctr++] = batteryStatus[i];
            }
        }
        catch(Exception e) {
            System.out.println("Could not Get String bytes");
            e.printStackTrace();
            return null;
        }
        byte[] tstamp = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(status.timestamp).array();
        for(int i = 0; i < tstamp.length; i++) {
            bytes[ctr++] = tstamp[i];
        }
        for(int k = 0; k < status.weather.length; k++) {
            byte[] weather = ByteBuffer.allocate((Integer.SIZE / Byte.SIZE)).putInt(status.weather[k]).array();
            for(int i = 0; i < weather.length; i++) {
                bytes[ctr++] = weather[i];
            }
        }
        return bytes; 

    }

    protected static int getStatusSize(Status status) {
        try {
            return 28 + getStringBytes(status.batteryStatus);
        } catch(Exception e) {
            System.out.println("Could not get Status size");
            e.printStackTrace();
            return 0;
        }
    }
    
    private static int getStringBytes(String string) throws UnsupportedEncodingException {
        return string.getBytes("UTF-8").length;
    }
    
}
