package managers;

import exceptions.NoElementException;
import exceptions.ReplayIdException;
import exceptions.RootException;
import exceptions.WrongArgumentException;
import managers.commands.BaseCommand;
import managers.generators.IdGenerator;
import recources.Organization;
import recources.comparators.OrganizationEmCoComparator;
import recources.comparators.OrganizationNameComparator;
import system.Request;
import system.Server;
import system.TextColor;

import java.io.IOException;
import java.util.*;

public class Receiver {
    public static String getAverageEmp(Request request) throws WrongArgumentException {
        if (request.getMessage().split(" ").length == 1) {
            return "Average of employees count is " + CollectionManager.getAverageOfEmployees();
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
        StringBuilder text = new StringBuilder("");
        Hashtable<String, Organization> table = CollectionManager.getTable();

        List<Organization> coll = new ArrayList<>(table.values().stream().toList());
        Collections.sort(coll);
        for (Organization o: coll) {
            text.append(o).append("\n");
        }
        return text.toString();
    }

    public static String removeByKey(Request request) throws NoElementException {
        System.out.println(TextColor.ANSI_BLUE + "Start executing command..." + TextColor.ANSI_RESET);
        CollectionManager.remove(request.getMessage().split(" ")[1]);
        return "Element was removed";
    }

    public static String removeGreater(Request request) throws NoElementException {
        Organization organisation = request.getOrganization();
        Hashtable<String, Organization> table = CollectionManager.getTable();
        OrganizationEmCoComparator c1 = new OrganizationEmCoComparator();
        OrganizationNameComparator c2 = new OrganizationNameComparator();
        ArrayList<String> keySet = new ArrayList<>();

        for (String key : table.keySet()) {
            if (c1.compare(organisation, table.get(key)) < 0) {
                keySet.add(key);
            } else if (c1.compare(organisation, table.get(key)) == 0) {
                if (c2.compare(organisation, table.get(key)) < 0) {
                    keySet.add(key);
                }
            }
        }
        int k = keySet.size();
        for (String key : keySet) {
            CollectionManager.remove(key);
        }
        if (k == CollectionManager.getTable().size()){
            return "Nothing was changed";
        }
        return "element was deleted";
    }

    public static String replaceIfGreater(Request request) throws NoElementException, ReplayIdException {
        String key = request.getMessage().split(" ")[1];
        Hashtable<String, Organization> table = CollectionManager.getTable();
        OrganizationEmCoComparator c1 = new OrganizationEmCoComparator();
        if (table.containsKey(key)) {
            Organization organization = request.getOrganization();
            if (c1.compare(organization, table.get(key)) > 0) {
                CollectionManager.remove(key);
                CollectionManager.add(key, organization);
            }
        }
        return "cheese";
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
        return "Data was saved";
    }

    public static String showData(Request request) throws WrongArgumentException {
        StringBuilder text = new StringBuilder();
        if (request.getMessage().split(" ").length == 1) {
            Hashtable<String, Organization> table = CollectionManager.getTable();
            for (String x : table.keySet()) {
                text.append(x).append(": ").append(table.get(x)).append("\n");
            }
            if (table.isEmpty()) {
                text.append(CollectionManager.getTable().getClass().getName()).append(" is empty").append("\n");
            }
            return text.toString();
        } else{
            throw new WrongArgumentException("command parameter");
        }
    }

    public static String showMinById(Request request){
        Organization organization = null;
        Hashtable<String, Organization> table = CollectionManager.getTable();
        for (String key : table.keySet()) {
            if (organization == null) {
                organization = table.get(key);
            } else if (organization.compareTo(table.get(key)) > 0) {
                organization = table.get(key);
            }
        }
        return organization.toString();
    }

    public static String updateByKey(Request request) throws NoElementException, ReplayIdException {
        boolean elementInCollection = false;
        Long id = Long.parseLong(request.getMessage().split(" ")[1]);

        for (String key : CollectionManager.getTable().keySet()) {
            if (CollectionManager.getTable().get(key).getId().equals(id)) {
                elementInCollection = true;

                Organization organization = request.getOrganization();
                CollectionManager.remove(key);
                CollectionManager.add(key, organization);
            }
        }
        if (!elementInCollection) {
            throw new NoElementException(id);
        }
        return "Element was updated";
    }
}
