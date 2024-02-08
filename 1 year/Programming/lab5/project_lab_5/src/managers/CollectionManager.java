package managers;

import data.Organization;
import exceptions.NoElementException;

import java.time.LocalDate;
import java.util.Hashtable;

public class CollectionManager {
    private static Hashtable<String, Organization> table;
    private static LocalDate date;

    public CollectionManager() {
        date = LocalDate.now();
        table = new Hashtable<>();
    }

    public static LocalDate getInitDate() {
        return date;
    }

    public static void add(String key, Organization organization) {
        table.put(key, organization);
    }

    public static void remove(String key) throws NoElementException {
        if (table == null) {
            throw new NoElementException(key);
        } else if (!CollectionManager.table.containsKey(key)) {
            throw new NoElementException(key);
        } else {
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
