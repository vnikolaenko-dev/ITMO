package filelogic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import recources.Organization;
import exceptions.RootException;
import managers.CollectionManager;

import java.io.*;
import java.util.Hashtable;

/**
 * Данный класс выполняет запись данных в формате XML
 *
 * @author vnikolaenko
 * @see Hashtable
 * @since 1.0
 */
public class WriterXML {
    /**
     * Записывает данные коллекции в файл
     *
     * @see CollectionManager
     * @param path путь до файла
     * @throws Exception если возникла проблема
     */
    public static void write(String path) throws IOException, RootException {
        File file = new File(path);

        if (!file.canWrite()){
            throw new RootException("You do not have enough rights to write to the file");
        }
        StringBuilder xml = new StringBuilder("""
                <?xml version="1.0" encoding="UTF-8" ?>

                <collection>
                \t<organizations>
                """);

        Hashtable<String, Organization> table = CollectionManager.getTable();
        for (String key : table.keySet()) {
            xml.append("\t\t<organization ");
            xml.append("key=\"").append(key).append("\" ");
            Organization organization = table.get(key);
            xml.append(organization.toXML()).append("/>\n");
        }

        xml.append("\t</organizations>\n" + "</collection>");

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
        bufferedOutputStream.write(xml.toString().getBytes());
        bufferedOutputStream.close();


    }
}
