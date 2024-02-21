package managers.commands;

import data.comparators.OrganizationEmCoComparator;
import data.Organization;
import managers.CollectionManager;

import java.util.*;

/**
 * Данная команда выводит элементы в порядке убывания
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class PrintDescendingCommand implements BaseCommand {
    @Override
    public void execute(String[] args) throws Exception {
        // ArrayList<Organization> co = new ArrayList<>();
        Hashtable<String, Organization> table = CollectionManager.getTable();

        List<Organization> coll = new ArrayList<>(table.values().stream().toList());
        Collections.sort(coll);
        for (Organization o: coll) {
            System.out.println(o);
        }
        /*
        System.out.println(Collections.sort(coll));

        for (String key : table.keySet()) {
            co.add(table.get(key));
        }
        co.sort(new OrganizationEmCoComparator());
        for (Organization o : co) {
            System.out.println(o);
        }
        */
    }

    @Override
    public String getName() {
        return "print_descending";
    }

    @Override
    public String getDescription() {
        return "print all element from smaller to bigger";
    }
}
