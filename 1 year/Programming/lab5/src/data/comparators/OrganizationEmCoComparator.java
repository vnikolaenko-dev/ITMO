package data.comparators;

import data.Organization;

import java.util.Comparator;

/**
 * Сравните организации по количеству сотрудников
 *
 * @author vnikolaenko
 * @see Organization
 * @since 1.0
 */
public class OrganizationEmCoComparator implements Comparator<Organization> {
    @Override
    public int compare(Organization s1, Organization s2) {
        return s1.getEmployeesCount().compareTo(s2.getEmployeesCount());
    }
}

