package managers.commands;

import recources.Organization;
import managers.CollectionManager;

import java.util.Hashtable;

/**
 * Данная команда выводит элемент с минимальным ID
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class ShowMinByIdCommand implements BaseCommand {
    @Override
    public String execute(String[] args) throws Exception {
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

    @Override
    public String getName() {
        return "min_by_id";
    }

    @Override
    public String getDescription() {
        return "show element with the less ID";
    }
}
