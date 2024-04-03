package managers;

import exceptions.ReplayIdException;
import recources.Organization;
import managers.generators.IdGenerator;
import exceptions.NoElementException;
import recources.comparators.OrganizationEmCoComparator;
import recources.comparators.OrganizationNameComparator;

import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Данный класс отвечает за взаимодействие с коллекцией
 * Содержит коллекцию команд
 *
 * @see managers.commands.BaseCommand
 * @see Organization
 * @author vnikolaenko
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
     * @param key ключ
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
    public static Double getAverageOfEmployees() {
        if (table == null) {
            return (double) 0;
        }
        int count = 0;
        for (String key : table.keySet()) {
            count += table.get(key).getEmployeesCount();
        }
        return (double) (count / table.size());
    }

    public static String removeGrater(Organization organisation){
        OrganizationEmCoComparator c1 = new OrganizationEmCoComparator();
        OrganizationNameComparator c2 = new OrganizationNameComparator();

        List<String> keySet = table.keySet().stream()
                .filter(key -> c1.compare(organisation, table.get(key)) < 0 ||
                        (c1.compare(organisation, table.get(key)) == 0 && c2.compare(organisation, table.get(key)) < 0))
                .collect(Collectors.toList());
        int k = keySet.size();
        // keySet.forEach(CollectionManager::remove);
        return "";
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
