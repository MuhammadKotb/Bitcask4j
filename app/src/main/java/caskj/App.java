
package caskj;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

import com.google.common.primitives.Longs;







class Student implements Serializable{
    public int id = 1;
    public int name = 1;
}
public class App {
	
		
    public static void main(String[] args) {
        System.out.println("Hello, Bitcask");
        byte[] bytes = Longs.toByteArray(System.currentTimeMillis());
        System.out.println(bytes.length);        
        Student std = new Student();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(System.currentTimeMillis());
            out.flush();
            byte[] bytes2 = bos.toByteArray();
            out.close();
            bos.close();
            System.out.println(bytes2.length);   
            byte[] bytes3 = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(System.currentTimeMillis()).array();
            System.out.println(bytes3.length);   
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }
}
