package managers.commands;

import data.comparators.OrganizationEmCoComparator;
import data.comparators.OrganizationNameComparator;
import data.Organization;
import data.generators.OrganizationGenerator;
import managers.CollectionManager;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Данная команда удаляет из коллекции все элементы, превышающие заданный
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class RemoveGraterCommand implements BaseCommand {
    @Override
    public void execute(String[] args) throws Exception {
        Organization organisation = OrganizationGenerator.createOrganization(0L);
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
            System.out.println("Nothing was changed");
        }
    }

    @Override
    public String getName() {
        return "remove_grater {element}";
    }

    @Override
    public String getDescription() {
        return "remove elements with grater than the element";
    }
}
