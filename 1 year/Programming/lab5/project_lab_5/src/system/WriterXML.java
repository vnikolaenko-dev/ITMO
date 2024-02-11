package system;

import data.Organization;
import managers.CollectionManager;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Hashtable;

public class WriterXML {
    public static void write(String path) throws Exception{
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "\n" +
                "<collection>\n";

        Hashtable<String, Organization> table = CollectionManager.getTable();
        for (String key: table.keySet()){
            xml += "\t<organisation>\n";
            xml += "\t\t<key>" + key + "</key>\n";
            Organization organization = table.get(key);
            xml += "\t\t<id>" + organization.getId() + "</id>\n";
            xml += "\t\t<name>" + organization.getName() + "</name>\n";
            xml += "\t\t<coordinates>\n";
            xml += "\t\t\t<x>" + organization.getCoordinates().getX() + "</x>\n";
            xml += "\t\t\t<y>" + organization.getCoordinates().getY() + "</y>\n";
            xml += "\t\t</coordinates>\n";
            xml += "\t\t<creationDate>" + organization.getCreationDate().toString() + "</creationDate>\n";
            xml += "\t\t<annualTurnover>" + organization.getAnnualTurnover() + "</annualTurnover>\n";
            xml += "\t\t<fullName>" + organization.getFullName() + "</fullName>\n";
            xml += "\t\t<employeesCount>" + organization.getEmployeesCount() + "</employeesCount>\n";
            xml += "\t\t<type>" + organization.getType().toString() + "</type>\n";
            xml += "\t\t<postalAddress>\n";
            xml += "\t\t\t<street>" + organization.getCoordinates().getX() + "</street>\n";
            xml += "\t\t\t<zipCode>" + organization.getCoordinates().getY() + "</zipCode>\n";
            xml += "\t\t</postalAddress>\n";
            xml += "\t</organisation>\n";
        }

        xml += "</collection>";
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
        outputStreamWriter.write(xml);
        outputStreamWriter.close();
    }
}
