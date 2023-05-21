
package caskj;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;




public class App {
	
		
    public static void main(String[] args) {
        Bitcask bitcask = new Bitcask4j();

        try {
            BitcaskHandle handle = bitcask.open(new File("/home/kotb/bitcaskDir"));
            
            for(int i = 0; i < 20000; i++) {
                bitcask.put(handle, 1511, 5151);
                System.out.println(handle.getCurrentFile());
                System.out.println(handle.getOffset());
            }


        }
        catch(Exception e) {
            e.printStackTrace();
        }

        
    }
}
