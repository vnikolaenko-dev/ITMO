package system;

import data.Organization;
import exceptions.ReplayIdException;
import exceptions.WrongArgumentException;
import managers.CollectionManager;
import managers.InputValidator;
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
        var br = new BufferedReader(new InputStreamReader(
                new FileInputStream(path), StandardCharsets.UTF_8));
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

        NodeList employeeElements = document.getDocumentElement().getElementsByTagName("organisation");

        // Перебор всех элементов employee
        for (int i = 0; i < employeeElements.getLength(); i++) {
            Node employee = employeeElements.item(i);

            // Получение атрибутов каждого элемента
            NamedNodeMap attributes = employee.getAttributes();
            String[] data = {attributes.getNamedItem("key").getNodeValue(), attributes.getNamedItem("id").getNodeValue(),
                    attributes.getNamedItem("name").getNodeValue(), attributes.getNamedItem("x").getNodeValue(),
                    attributes.getNamedItem("y").getNodeValue(), attributes.getNamedItem("creationDate").getNodeValue(),
                    attributes.getNamedItem("annualTurnover").getNodeValue(), attributes.getNamedItem("fullName").getNodeValue(),
                    attributes.getNamedItem("employeesCount").getNodeValue(), attributes.getNamedItem("type").getNodeValue(),
                    attributes.getNamedItem("street").getNodeValue(), attributes.getNamedItem("zipCode").getNodeValue()};

            String key = data[0];
            // проверяем корректность всех значений
            try {
                InputValidator.idIsOk(data[1]);
                InputValidator.inputIsNotEmpty(data[2], "NAME");
                InputValidator.coordinatesIsOk(data[3]);
                InputValidator.coordinatesIsOk(data[4]);
                InputValidator.inputIsNotEmpty(data[5], "DATE");
                InputValidator.annualTurnoverIsOk(data[6]);
                InputValidator.inputIsNotEmpty(data[7], "FullName");
                InputValidator.employeesCountIsOk(data[8]);
                InputValidator.typeIsOk(data[9]);
                InputValidator.inputIsNotEmpty(data[10], "STREET");
                InputValidator.inputIsNotEmpty(data[11], "ziCode");
            } catch (Exception e) {
                throw e;
            }
            Organization organization = new Organization(data);
            CollectionManager.add(key, organization);
        }
    }
}
