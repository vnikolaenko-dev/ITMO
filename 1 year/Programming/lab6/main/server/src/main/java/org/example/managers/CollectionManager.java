package org.example.managers;

import org.example.exceptions.ReplayIdException;
import org.example.recources.Organization;
import org.example.managers.generators.IdGenerator;
import org.example.exceptions.NoElementException;
import org.example.recources.comparators.OrganizationEmCoComparator;
import org.example.recources.comparators.OrganizationNameComparator;
import org.example.system.Request;

import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Данный класс отвечает за взаимодействие с коллекцией
 * Содержит коллекцию команд
 *
 * @author vnikolaenko
 * @see org.example.managers.commands.BaseCommand
 * @see Organization
 * @since 1.0
 */
public class CollectionManager {
    private static CollectionManager instance;
    private static Hashtable<String, Organization> table = new Hashtable<>();
    private static LocalDate date = LocalDate.now();

    /**
     * Базовый конструктор
     */
    private CollectionManager() {
        table = new Hashtable<>();
        date = LocalDate.now();
    }

    public static synchronized CollectionManager getInstance() {
        if (instance == null) {
            instance = new CollectionManager();
        }
        return instance;
    }

    /**
     * Получить дату инициализации коллекции
     *
     * @return дата инициализации
     */
    public static LocalDate getInitDate() {
        return date;
    }


    /**
     * Добавить новую организацию по ключу
     *
     * @param key          ключ
     * @param organization организация
     */
    public static void add(String key, Organization organization) throws ReplayIdException {
        if (table == null) {
            table = new Hashtable<>();
        }
        table.put(key, organization);
    }

    /**
     * Удалить новую организацию по ключу
     *
     * @param key ключ
     */
    public static void remove(String key) throws NoElementException {
        if (table == null || !CollectionManager.table.containsKey(key)) {
            throw new NoElementException(key);
        } else {
            IdGenerator.remove(CollectionManager.table.get(key).getId());
            table.remove(key);
        }
    }

    /**
     * Получить среднее значение числа работников
     *
     * @return среднее число работников среди всех организаций
     */
    public static String getAverageOfEmployees() {
        if (table.isEmpty()) {
            return "Collection is empty";
        }
        double averageEmployeeCount = table.values().stream()
                .mapToInt(Organization::getEmployeesCount)
                .average()
                .orElse(0.0);

        return String.valueOf(averageEmployeeCount);
    }

    private static void removePr(String key) {
        table.remove(key);
    }

    public static String removeGrater(Organization organisation) {
        if (table.isEmpty()) {
            return "Collection is empty";
        }
        OrganizationEmCoComparator c1 = new OrganizationEmCoComparator();
        OrganizationNameComparator c2 = new OrganizationNameComparator();

        List<String> keySet = table.keySet().stream()
                .filter(key -> c1.compare(organisation, table.get(key)) < 0 ||
                        (c1.compare(organisation, table.get(key)) == 0 && c2.compare(organisation, table.get(key)) < 0))
                .collect(Collectors.toList());
        int k = keySet.size();
        keySet.forEach(CollectionManager::removePr);
        if (k == keySet.size()) {
            return "Nothing was changed";
        }
        return "Table was changed";
    }

    public static String replaceIfGreater(Request request) {
        if (table.isEmpty()) {
            return "Collection is empty";
        }
        String key = request.getKey();
        OrganizationEmCoComparator c1 = new OrganizationEmCoComparator();
        table.entrySet().stream()
                .filter(entry -> entry.getKey().equals(key))
                .map(Map.Entry::getValue)
                .findFirst()
                .ifPresent(organization -> {
                    if (c1.compare(organization, table.get(key)) > 0) {
                        CollectionManager.removePr(key);
                        try {
                            CollectionManager.add(key, request.getOrganization());
                        } catch (ReplayIdException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
        return "Changed was accepted";
    }

    public static String showData(Request request) {
        if (table.isEmpty()) {
            return "Collection is empty";
        }
        StringBuilder text = new StringBuilder();
        if (request.getMessage().split(" ").length == 1) {
            Hashtable<String, Organization> table = CollectionManager.getTable();

            text.append(table.entrySet().stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue())
                    .collect(Collectors.joining("\n")));

            if (table.isEmpty()) {
                text.append(CollectionManager.getTable().getClass().getName()).append(" is empty\n");
            }
        }
        return text.toString();
    }

    public static String getMinById() {
        if (table.isEmpty()) {
            return "Collection is empty";
        } else {
            Organization organization = table.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .reduce(null, (org1, org2) -> {
                        if (org1 == null) {
                            return org2;
                        } else if (org1.compareTo(org2) > 0) {
                            return org2;
                        } else {
                            return org1;
                        }
                    });

            return organization.toString();
        }
    }

    public static String removeById(Request request) {
        if (table.isEmpty()) {
            return "Collection is empty";
        }
        Long id = Long.parseLong(request.getMessage().split(" ")[1]);

        boolean elementInCollection = CollectionManager.getTable().entrySet().stream()
                .filter(entry -> entry.getValue().getId().equals(id))
                .map(entry -> {
                    Organization organization = request.getOrganization();
                    CollectionManager.removePr(entry.getKey());
                    try {
                        CollectionManager.add(entry.getKey(), organization);
                    } catch (ReplayIdException e) {
                        throw new RuntimeException(e);
                    }
                    return true;
                })
                .findFirst()
                .orElse(false);

        if (!elementInCollection) {
            return "Element with id " + id + " not in collection";
        }
        return "Element was updated";
    }

    public static String getDescending() {
        if (table.isEmpty()) {
            return "Collection is empty";
        }
        String text = table.values().stream()
                .sorted()
                .map(Object::toString)
                .collect(Collectors.joining("\n")) + "\n";

        return text;
    }

    /**
     * Получить коллекцию
     *
     * @return коллекция
     */
    public static Hashtable<String, Organization> getTable() {
        return table;
    }
}
