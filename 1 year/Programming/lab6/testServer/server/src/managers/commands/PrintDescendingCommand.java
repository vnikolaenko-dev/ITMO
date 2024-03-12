package managers.commands;

import recources.Organization;
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
    public String execute(String[] args) throws Exception {
        StringBuilder text = new StringBuilder("");
        Hashtable<String, Organization> table = CollectionManager.getTable();

        List<Organization> coll = new ArrayList<>(table.values().stream().toList());
        Collections.sort(coll);
        for (Organization o: coll) {
            text.append(o);
        }
        return text.toString();
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
