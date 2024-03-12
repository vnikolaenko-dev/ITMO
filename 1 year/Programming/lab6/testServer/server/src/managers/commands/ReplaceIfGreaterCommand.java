package managers.commands;

import recources.comparators.OrganizationEmCoComparator;
import recources.Organization;
import managers.generators.OrganizationGenerator;
import managers.CollectionManager;

import java.util.Hashtable;
/**
 * Данная команда заменяет значение по ключу, если новое значение больше старого
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class ReplaceIfGreaterCommand implements BaseCommand {
    @Override
    public String execute(String[] args) throws Exception {
        String key = args[1];
        Hashtable<String, Organization> table = CollectionManager.getTable();
        OrganizationEmCoComparator c1 = new OrganizationEmCoComparator();
        if (table.containsKey(key)) {
            Organization organization = OrganizationGenerator.createOrganization(0L);
            if (c1.compare(organization, table.get(key)) > 0) {
                CollectionManager.remove(key);
                CollectionManager.add(key, organization);
            }
        }
        return "pop";
    }

    @Override
    public String getName() {
        return "replace_if_greater";
    }

    @Override
    public String getDescription() {
        return "null {element} - update element by key if new bigger than element in collection with the same key";
    }
}
