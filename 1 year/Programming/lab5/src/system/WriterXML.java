package system;

import data.Organization;
import exceptions.ReplayIdException;
import exceptions.RootException;
import exceptions.WrongArgumentException;
import managers.CollectionManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedHashMap;

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
        if (!file.canRead()){
            throw new RootException("You do not have enough rights to write to the file");
        }
        StringBuilder xml = new StringBuilder("""
                <?xml version="1.0" encoding="UTF-8" ?>

                <collection>
                \t<organisations>
                """);

        Hashtable<String, Organization> table = CollectionManager.getTable();
        for (String key : table.keySet()) {
            xml.append("\t\t<organisation ");
            xml.append("key=\"").append(key).append("\" ");
            Organization organization = table.get(key);
            xml.append(organization.toXML()).append("/>\n");
        }

        xml.append("\t</organisations>\n" + "</collection>");

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
        bufferedOutputStream.write(xml.toString().getBytes());
        bufferedOutputStream.close();
    }
}
