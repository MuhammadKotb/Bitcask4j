package caskj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StatusWriter {
    
    
    protected static int write(FileOutputStream fos, Status status) throws IOException, NullPointerException {
        byte[] bytes = StatusUtil.getBytes(status);
        if(bytes == null) throw new NullPointerException();
        fos.write(bytes);
        return bytes.length;
    }      


}
