package mccomplexcrafter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 @author Noah
 */
public class ObjectWriter {
    public static boolean write(Object o, String loc) throws IOException {
        new File(loc).delete();
        ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(loc));
        fos.writeObject(o);
        fos.close();
        return true;
    }
    
    public static Object read(String loc) throws IOException, ClassNotFoundException {
        try {
            if(!new File(loc).exists()) return false;
            ObjectInputStream fis = new ObjectInputStream(new FileInputStream(loc));
            return fis.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new Error("Could not read file!");
        }
    }

    private ObjectWriter() {
    }
}
