package system;

import data.Organization;
import managers.CollectionManager;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Hashtable;

public class WriterXML {
    public static void write(String path) throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "\n" +
                "<collection>" +
                "\n\t<organisations>\n";

        Hashtable<String, Organization> table = CollectionManager.getTable();
        for (String key : table.keySet()) {
            xml += "\t\t<organisation ";
            xml += "key=\"" + key + "\" ";
            Organization organization = table.get(key);
            xml += organization.toXML() + "/>\n";
        }

        xml += "\t</organisations>\n" +
                "</collection>";

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
        bufferedOutputStream.write(xml.getBytes());
        bufferedOutputStream.close();

        /*
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
        outputStreamWriter.write(xml);
        outputStreamWriter.close();
         */
    }
}
