package org.example.managers;

import org.example.comparators.OrganizationEmCoComparator;
import org.example.comparators.OrganizationNameComparator;
import org.example.exceptions.ReplayIdException;
import org.example.*;
import org.example.exceptions.NoElementException;
import org.example.exceptions.WrongArgumentException;
import org.example.system.Request;

import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * Данный класс отвечает за взаимодействие с коллекцией
 * Содержит коллекцию
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
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

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
    public static void addOrganization(String key, Organization organization) throws ReplayIdException {
        lock.writeLock().lock();
        if (table == null) {
            table = new Hashtable<>();
        }
        table.put(key, organization);
        lock.writeLock().unlock();
    }


    /**
     * Получить среднее значение числа работников
     *
     * @return среднее число работников среди всех организаций
     */
    public static String getAverageOfEmployees() {
        lock.readLock().lock();
        if (table.isEmpty()) {
            return "Collection is empty";
        }
        double averageEmployeeCount = table.values().stream()
                .mapToInt(Organization::getEmployeesCount)
                .average()
                .orElse(0.0);
        lock.readLock().unlock();
        return String.valueOf(averageEmployeeCount);
    }

    public static String showData() {
        lock.readLock().lock();
        if (table.isEmpty()) {
            return "Collection is empty";
        }
        StringBuilder text = new StringBuilder();

        Hashtable<String, Organization> table = CollectionManager.getTable();

        text.append(table.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n")));

        if (table.isEmpty()) {
            text.append(CollectionManager.getTable().getClass().getName()).append(" is empty\n");
        }
        lock.readLock().unlock();
        return text.toString();
    }

    public static String getMinById() {
        lock.readLock().lock();
        if (table.isEmpty()) {
            return "Collection is empty";
        } else {
            Organization organization = table.values().stream()
                    .reduce(null, (org1, org2) -> {
                        if (org1 == null) {
                            return org2;
                        } else if (org1.compareTo(org2) > 0) {
                            return org2;
                        } else {
                            return org1;
                        }
                    });
            lock.readLock().unlock();
            return organization.toString();
        }
    }

    public static String getOrganizationKeyById(int id) throws WrongArgumentException {
        lock.readLock().lock();
        for (String key: table.keySet()){
            if (table.get(key).getId() == id){
                return key;
            }
        }
        lock.readLock().unlock();
        throw new WrongArgumentException("Id");
    }

    public static String getDescending() {
        if (table.isEmpty()) {
            return "Collection is empty";
        }
        lock.readLock().lock();
        String answer = table.values().stream()
                .sorted()
                .map(Object::toString)
                .collect(Collectors.joining("\n")) + "\n";
        lock.readLock().unlock();
        return answer;
    }

    public static Hashtable<String, Organization> getTable() {
        return table;
    }

    public static void setInstance(CollectionManager instance) {
        CollectionManager.instance = instance;
    }

    public static void setTable(Hashtable<String, Organization> table) {
        lock.writeLock().lock();
        CollectionManager.table = table;
        lock.writeLock().unlock();
    }

    public static LocalDate getDate() {
        return date;
    }

    public static void setDate(LocalDate date) {
        CollectionManager.date = date;
    }
}
