package org.example.managers;

import org.example.exceptions.RootException;
import org.example.filelogic.ReaderXML;
import org.example.filelogic.WriterXML;

import java.io.*;

public class FileManager {
    public static void readFromPath(String path) throws Exception {
        ReaderXML.read(path);
    }

    public static void writeToPath(String path) throws IOException, RootException {
        WriterXML.write(path);
    }
}
