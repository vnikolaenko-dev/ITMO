package org.example.managers;

import org.example.Organization;
import org.example.comparators.OrganizationEmCoComparator;
import org.example.exceptions.WrongArgumentException;
import org.example.managers.commands.BaseCommand;
import org.example.system.Request;
import org.example.system.TextColor;

import java.util.*;

public class Receiver {
    public static String getAverageEmp() {
        return CollectionManager.getAverageOfEmployees();
    }

    public static String clear(Request request) throws WrongArgumentException {
        DataBaseManager.clear(request.getLogin());
        downloadData();
        return "Table is clear";
    }

    public static String exit() {
        System.exit(1);
        return "";
    }

    public static String getHelp() {
        StringBuilder text = new StringBuilder();
        LinkedHashMap<String, BaseCommand> commandList = CommandManager.getCommandList();
        for (String name : commandList.keySet()) {
            BaseCommand command = commandList.get(name);
            text.append(TextColor.ANSI_BLUE).append(command.getName()).append(TextColor.ANSI_RESET).append(" - ").append(command.getDescription()).append("\n");
        }
        return text.toString();

    }

    public static String getHistory() {
        StringBuilder text = new StringBuilder();
        String[] sp = new String[6];
        int n = 0;
        for (BaseCommand command : CommandManager.getLastSixCommand()) {
            sp[n] = command.getName();
            n += 1;
        }
        for (int i = 0; i < 6; i++) {
            if (!(sp[i] == null)) {
                text.append("-").append(sp[i]).append("\n");
            }
        }
        return text.toString();

    }

    public static String getInfo() {
        return "Data type - " + CollectionManager.getTable().getClass().getName() +
                "\nCount of organization - " + CollectionManager.getTable().keySet().size() +
                "\nInit date - " + CollectionManager.getInitDate();
    }

    public static String insertNewElem(Request request) throws WrongArgumentException {
        if (!CollectionManager.getTable().containsKey(request.getMessage().split(" ")[1])) {
            if (DataBaseManager.insertOrganization(request.getOrganization(), request.getLogin(), request.getKey())) {
                downloadData();
                return "Element was added";
            } else return "Something wrong";
        } else throw new WrongArgumentException("KEY");
    }


    public static String removeByKey(Request request) {
        if (DataBaseManager.removeOrganizationByKey(request.getLogin(), request.getMessage().split(" ")[1])) {
            downloadData();
            return "Element was removed";
        } else return "Element wasn't removed";
    }

    public static void downloadData() {
        CollectionManager.setTable(DataBaseManager.getDataFromDatabase());
    }

    public static String getDescending() {
        return CollectionManager.getDescending();
    }

    public static String removeGreater(Request request) {
        StringBuilder message = new StringBuilder("");
        OrganizationEmCoComparator c = new OrganizationEmCoComparator();
        for (String key : CollectionManager.getTable().keySet()) {
            if (c.compare(request.getOrganization(), CollectionManager.getTable().get(key)) > 0) {
                if (DataBaseManager.removeOrganizationByKey(request.getLogin(), key)) {
                    message.append("element with key '").append(key).append("' was deleted").append("\n");
                    downloadData();
                }
            }
        }
        if (message.isEmpty()){
            return "Nothing changed";
        }
        else{
            return message.toString();
        }
    }

    public static String replaceIfGrater(Request request) {
        String key = request.getMessage().split(" ")[1];
        OrganizationEmCoComparator c = new OrganizationEmCoComparator();
        if (c.compare(request.getOrganization(), CollectionManager.getTable().get(key)) > 0) {
            if (DataBaseManager.updateOrganizationById(Integer.parseInt(String.valueOf(CollectionManager.getTable().get(key).getId())), key, request.getLogin(), request.getOrganization())) {
                downloadData();
                return "Element was updated";
            } else return "Element wasn't updated";
        } else return "Element is less then new";
    }

    public static String showData() {
        return CollectionManager.showData();
    }

    public static String showMinById() {
        return CollectionManager.getMinById();
    }

    public static String updateById(Request request) {
        try {
            int id = Integer.parseInt(request.getMessage().split(" ")[1]);
            String key = CollectionManager.getOrganizationKeyById(id);
            if (DataBaseManager.updateOrganizationById(id, key, request.getLogin(), request.getOrganization())) {
                downloadData();
                return "Element was updated";
            } else return "Element wasn't updated";
        } catch (WrongArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
