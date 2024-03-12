package managers;

import exceptions.RootException;
import filelogic.ReaderXML;
import filelogic.WriterXML;

import java.io.*;

public class FileManager {
    public static void readFromPath(String path) throws Exception {
        ReaderXML.read(path);
    }

    public static void writeToPath(String path) throws IOException, RootException {
        WriterXML.write(path);
    }
}
