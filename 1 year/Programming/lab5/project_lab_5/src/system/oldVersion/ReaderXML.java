package system.oldVersion;

import data.Organization;
import managers.CollectionManager;
import managers.InputValidator;
import java.io.*;
import java.util.ArrayList;

public class ReaderXML {
    public static void old_read(String path) throws Exception {
        new CollectionManager();
        int count = 0;
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(path), "UTF-8");
        String text = "";
        while (true) {
            int n = inputStreamReader.read();
            if (n != -1) {
                char x = (char) n;
                text += x;
            } else break;
        }

        String[] sp = text.split("<organisation>");
        for (String elem : sp) {
            if (elem.indexOf("key") > 0) {
                ArrayList<String> data = new ArrayList<>();
                for (String line : elem.split("\n")) {
                    int begin = line.indexOf(">");
                    int end = line.lastIndexOf("<");
                    if (begin < end && begin > 0) {
                        data.add(line.substring(begin + 1, end));
                    }
                }
                String key = data.get(0);
                // проверяем корректность всех значений
                try {
                    InputValidator.idIsOk(data.get(1));
                    InputValidator.inputIsNotEmpty(data.get(2), "NAME");
                    InputValidator.coordinatesIsOk(data.get(3));
                    InputValidator.coordinatesIsOk(data.get(4));
                    InputValidator.inputIsNotEmpty(data.get(5), "DATE");
                    InputValidator.annualTurnoverIsOk(data.get(6));
                    InputValidator.inputIsNotEmpty(data.get(7), "FullName");
                    InputValidator.employeesCountIsOk(data.get(8));
                    InputValidator.typeIsOk(data.get(9));
                    InputValidator.inputIsNotEmpty(data.get(10), "STREET");
                    InputValidator.inputIsNotEmpty(data.get(11), "ziCode");
                } catch (Exception e) {
                    throw e;
                }
                Organization organization = new Organization(data);
                CollectionManager.add(key, organization);
                count++;
            }
        }
        System.out.println(count + " organisations were downloaded.");
    }
}
