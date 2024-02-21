package data.comparators;

import data.Organization;

import java.util.Comparator;

/**
 * Сравните организации по названию
 *
 * @author vnikolaenko
 * @see Organization
 * @since 1.0
 */
public class OrganizationNameComparator implements Comparator<Organization> {
    @Override
    public int compare(Organization s1, Organization s2) {
        return s1.getName().compareTo(s2.getName());
    }
}
