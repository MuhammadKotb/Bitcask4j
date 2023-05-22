
package caskj;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;






public class App {
	
		
    public static void main(String[] args) {
        Bitcask bitcask = new Bitcask4j();
        Status status = new Status();
        status.stationId = 3;
        status.statusNo = 220;
        status.batteryStatus = "low";
        status.timestamp = System.currentTimeMillis();
        status.weather[0] = 55;
        status.weather[1] = 90;
        status.weather[2] = 30;

        try {
            BitcaskHandle handle = bitcask.open(new File("/home/kotb/bitcaskDir"));
            
            bitcask.put(handle, 1511, status);
            System.out.println(handle.getCurrentFile());
            System.out.println(handle.getOffset());


        }
        catch(Exception e) {
            e.printStackTrace();
        }

        
    }
}
