package org.example.managers;

import org.example.exceptions.NoElementException;
import org.example.exceptions.ReplayIdException;
import org.example.exceptions.RootException;
import org.example.exceptions.WrongArgumentException;
import org.example.managers.commands.BaseCommand;
import org.example.managers.generators.IdGenerator;
import org.example.recources.Organization;
import org.example.recources.comparators.OrganizationEmCoComparator;
import org.example.recources.comparators.OrganizationNameComparator;
import org.example.system.Request;
import org.example.system.Server;
import org.example.system.TextColor;

import java.io.IOException;
import java.util.*;

import static org.example.system.Main.LOGGER;

public class Receiver {
    public static String getAverageEmp(Request request) throws WrongArgumentException {
        if (request.getMessage().split(" ").length == 1) {
            return CollectionManager.getAverageOfEmployees();
        } else{
            throw new WrongArgumentException("command parameter");
        }
    }

    public static String clearTable(Request request) throws WrongArgumentException {
        if (request.getMessage().split(" ").length == 1) {
            CollectionManager.getTable().clear();
            return "Table is clear";
        } else{
            throw new WrongArgumentException("command parameter");
        }
    }

    public static String exit(Request request) throws WrongArgumentException {
        if (request.getMessage().split(" ").length == 1) {
            System.exit(1);
            return "";
        } else{
            throw new WrongArgumentException("command parameter");
        }
    }

    public static String getHelp(Request request) throws WrongArgumentException {
        StringBuilder text = new StringBuilder("");
        if (request.getMessage().split(" ").length == 1) {
            LinkedHashMap<String, BaseCommand> commandList = CommandManager.getCommandList();
            for (String name: commandList.keySet()){
                BaseCommand command = commandList.get(name);
                text.append(TextColor.ANSI_BLUE).append(command.getName()).append(TextColor.ANSI_RESET).append(" - ").append(command.getDescription()).append("\n");
            }
            return text.toString();
        } else{
            throw new WrongArgumentException("command parameter");
        }
    }

    public static String getHistory(Request request) throws WrongArgumentException {
        StringBuilder text = new StringBuilder();
        if (request.getMessage().split( " ").length == 1) {
            String[] sp = new String[6];
            int n = 0;
            for (BaseCommand command : CommandManager.lastSixCommand) {
                sp[n] = command.getName();
                n += 1;
            }
            for (int i = 0; i < 6; i++) {
                if (!(sp[i] == null)) {
                    text.append("-" + sp[i]).append("\n");
                }
            }
            return text.toString();
        } else{
            throw new WrongArgumentException("command parameter");
        }
    }

    public static String getInfo(Request request) throws WrongArgumentException {
        StringBuilder text = new StringBuilder("");
        if (request.getMessage().split( " ").length == 1) {
            text.append("Data type - " + CollectionManager.getTable().getClass().getName());
            text.append("\nCount of organization - " + CollectionManager.getTable().keySet().size());
            text.append("\nInit date - " + CollectionManager.getInitDate());
            return text.toString();
        } else{
            throw new WrongArgumentException("command parameter");
        }
    }

    public static String insertNewElem(Request request) throws ReplayIdException, WrongArgumentException {
        request.getOrganization().setId(IdGenerator.generateId());
        if (request.getMessage().split( " ").length == 2 && !CollectionManager.getTable().containsKey(request.getMessage().split(" ")[1])) {
            CollectionManager.add(request.getKey(), request.getOrganization());
            return "Element was added";
        } else throw new WrongArgumentException("KEY");
    }

    public static String getDescending(Request request){
        return CollectionManager.getDescending();
    }

    public static String removeByKey(Request request) throws NoElementException {
        System.out.println(TextColor.ANSI_BLUE + "Start executing command..." + TextColor.ANSI_RESET);
        CollectionManager.remove(request.getMessage().split(" ")[1]);
        return "Element was removed";
    }

    public static String removeGreater(Request request) {
        return CollectionManager.removeGrater(request.getOrganization());
    }

    public static String replaceIfGreater(Request request) {
        return CollectionManager.replaceIfGreater(request);
    }

    public static String saveData(Request request) throws IOException, RootException, WrongArgumentException {
        if (request.getMessage().split(" ").length == 1) {
            try {
                FileManager.writeToPath(Server.data_path);
            } catch (Exception e){
                System.out.println(e.getMessage());
                throw e;
            }
        } else{
            throw new WrongArgumentException("command parameter");
        }
        LOGGER.info("Data was saved");
        return "Data was saved";
    }

    public static String showData(Request request) throws WrongArgumentException {
        return CollectionManager.showData(request);
    }

    public static String showMinById(Request request){
        return CollectionManager.getMinById();
    }

    public static String updateByKey(Request request) {
        return CollectionManager.removeById(request);
    }
}
