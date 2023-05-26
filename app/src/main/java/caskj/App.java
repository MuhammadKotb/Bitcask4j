
package caskj;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
// close -> 3




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
        MessageHandler messageHandler = new MessageHandler();
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
            while(true) {
                Socket socket = serverSocket.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                byte[] bytes = dis.readAllBytes();
                String response = messageHandler.handleMessage(bitcask, bytes);
                dis.close();
                dos.close();
                System.out.println(response);
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        
    }
}
