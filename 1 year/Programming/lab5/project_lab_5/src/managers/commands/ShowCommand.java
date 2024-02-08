package managers.commands;

import data.Organization;
import managers.CollectionManager;

import java.util.Hashtable;

public class ShowCommand implements BaseCommand {
    @Override
    public void execute(String[] args) {
        Hashtable<String, Organization> table = CollectionManager.getTable();
        for (String x : table.keySet()) {
            System.out.println(table.get(x));
        }
        if (table.isEmpty()) {
            System.out.println(CollectionManager.getTable().getClass().getName() + " is empty");
        }
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "show data";
    }
}
