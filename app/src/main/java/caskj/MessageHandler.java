package caskj;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

enum Response {
    COULD_NOT_CLOSE_BITCASK,
    COULD_NOT_OPEN_BITCASK,
    COULD_NOT_GET_STATUS,
    COULD_NOT_PUT_STATUS,
    OPENED_SUCCESSFULLY,
    CLOSED_SUCCESSULLY,
    PUT_SUCCESSFULLY,
    GET_SUCESSFULLY,
    UNEXPECTED_ERROR
}

public class MessageHandler {
    
    private BitcaskHandle handle = null;

    private static class Entry {
        public int key;
        public Status status;
        public Entry(int key, Status status) {
            this.key = key;
            this.status = status;
        }
    }
    public MessageHandler() {

    }
    public String handleMessage(Bitcask bitcask, byte[] bytes) {

        try {
            String method = decodeMethod(bytes);
            
            switch(method) {
                case "open" : {
                    String path = decodeOpen(bytes);
                    this.handle = bitcask.open(new File(path));
                    return Response.OPENED_SUCCESSFULLY.name();
                } 
                case "close" : {
                    if(this.handle == null) return Response.COULD_NOT_CLOSE_BITCASK.name();
                    bitcask.close(this.handle);
                    this.handle = null;
                    return Response.CLOSED_SUCCESSULLY.name();
                } 
                case "get" : {
                    if(this.handle == null) return Response.COULD_NOT_GET_STATUS.name();
                    bitcask.get(this.handle, decodeGet(bytes));
                    return Response.GET_SUCESSFULLY.name();
                } 
                case "put" : {
                    if(this.handle == null) return Response.COULD_NOT_PUT_STATUS.name();
                    Entry entry = decodePut(bytes);
                    bitcask.put(this.handle, entry.key, entry.status);
                    return Response.PUT_SUCCESSFULLY.name();
                }
                default: return Response.UNEXPECTED_ERROR.name();
            }

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return Response.UNEXPECTED_ERROR.name();
        }

    } 


    private static String decodeMethod(byte[] bytes) throws Exception {
        byte[] methodBytes = Arrays.copyOfRange(bytes, 0, 2);
        int method = ByteBuffer.wrap(methodBytes).getChar();
        switch(method) {
            case 0 : return "open";
            case 1 : return "get";
            case 2 : return "put";
            case 3 : return "close";
            default : throw new Exception("Could not decode method");    
        }
    }

    private static String decodeOpen(byte[] bytes) throws Exception {
        byte[] stringLenBytes = Arrays.copyOfRange(bytes, 2, 6);
        int stringLen = ByteBuffer.wrap(stringLenBytes).getInt(); 
        byte[] pathBytes = Arrays.copyOfRange(bytes, 6, 6 + stringLen);
        String path = new String(pathBytes, "UTF-8");
        return path;
    }

    private static int decodeGet(byte[] bytes) {
        byte[] keyBytes = Arrays.copyOfRange(bytes, 2, 6);
        return ByteBuffer.wrap(keyBytes).getInt();
    }

    private static Entry decodePut(byte[] bytes) {
        byte[] keyBytes = Arrays.copyOfRange(bytes, 2, 6);
        int key = ByteBuffer.wrap(keyBytes).getInt();

        byte[] statusLenBytes = Arrays.copyOfRange(bytes, 6, 10);
        int statusLen = ByteBuffer.wrap(statusLenBytes).getInt();

        byte[] statusBytes = Arrays.copyOfRange(bytes, 10, 10 + statusLen);
        Status status = StatusReader.statusFromBytes(statusBytes);


        return new Entry(key, status);

    }
}
