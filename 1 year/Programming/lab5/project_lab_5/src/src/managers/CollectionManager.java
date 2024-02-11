package managers;

import data.Organization;
import data.generators.IdGenerator;
import exceptions.NoElementException;

import java.time.LocalDate;
import java.util.Hashtable;

public class CollectionManager {
    private static Hashtable<String, Organization> table = new Hashtable<>();
    private static LocalDate date;

    public CollectionManager() {
        table = new Hashtable<>();
        date = LocalDate.now();
        new IdGenerator();
    }

    public static LocalDate getInitDate() {
        return date;
    }

    public static void add(String key, Organization organization) {
        if (table == null) {
            table = new Hashtable<>();
        }
        table.put(key, organization);
        IdGenerator.add(organization.getId());
    }

    public static void remove(String key) throws NoElementException {
        if (table == null) {
            throw new NoElementException(key);
        } else if (!CollectionManager.table.containsKey(key)) {
            throw new NoElementException(key);
        } else {
            IdGenerator.remove(CollectionManager.table.get(key).getId());
            table.remove(key);
        }
    }

    public static Hashtable<String, Organization> getTable() {
        return table;
    }

    public static void setTable(Organization table) {
        table = table;
    }
}
