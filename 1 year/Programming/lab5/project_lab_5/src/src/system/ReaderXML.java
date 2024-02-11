package system;

import data.Organization;
import exceptions.ReplayIdException;
import exceptions.WrongArgumentException;
import managers.CollectionManager;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ReaderXML {
    public static void read(String path) throws ParserConfigurationException, IOException, SAXException, ReplayIdException, WrongArgumentException {
        // Чтение из файла
        var br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        String line, text = "";
        while ((line = br.readLine()) != null) {
            text += line;
        }
        InputSource in = new InputSource(new StringReader(text));

        // Получение фабрики, чтобы после получить билдер документов.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
        DocumentBuilder builder = factory.newDocumentBuilder();
        // Запарсили XML, создав структуру Document. Теперь у нас есть доступ ко всем элементам, каким нам нужно.
        Document document = builder.parse(in);

        NodeList organisationElements = document.getDocumentElement().getElementsByTagName("organisation");

        // Перебор всех элементов employee
        for (int i = 0; i < organisationElements.getLength(); i++) {
            Node organisation = organisationElements.item(i);

            // Получение атрибутов каждого элемента
            NamedNodeMap attributes = organisation.getAttributes();
            String[] data = {attributes.getNamedItem("key").getNodeValue(), attributes.getNamedItem("id").getNodeValue(),
                    attributes.getNamedItem("name").getNodeValue(), attributes.getNamedItem("x").getNodeValue(),
                    attributes.getNamedItem("y").getNodeValue(), attributes.getNamedItem("creationDate").getNodeValue(),
                    attributes.getNamedItem("annualTurnover").getNodeValue(), attributes.getNamedItem("fullName").getNodeValue(),
                    attributes.getNamedItem("employeesCount").getNodeValue(), attributes.getNamedItem("type").getNodeValue(),
                    attributes.getNamedItem("street").getNodeValue(), attributes.getNamedItem("zipCode").getNodeValue()};

            String key = data[0];
            try {
                Organization organization = new Organization(data);
                CollectionManager.add(key, organization);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
