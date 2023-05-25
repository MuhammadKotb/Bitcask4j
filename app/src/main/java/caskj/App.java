
package caskj;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

// first byte
// open -> 0
// get -> 1
// put -> 2
// close -> 4




public class App {
	
		
    public static void main(String[] args) {
        Bitcask bitcask = new Bitcask4j();
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
            while(true) {
                Socket socket = serverSocket.accept();

            }
           
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        
    }
}
