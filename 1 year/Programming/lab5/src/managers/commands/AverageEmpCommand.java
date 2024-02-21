package managers.commands;

import managers.CollectionManager;

/**
 * Данная команда выводит среднее значение поля employeesCount (Organization) для всех элементов коллекции
 *
 * @see BaseCommand
 * @see data.Organization
 * @author vnikolaenko
 * @since 1.0
 */
public class AverageEmpCommand implements BaseCommand {
    @Override
    public void execute(String[] args) throws Exception {
        System.out.println("Average of employees count is " + CollectionManager.getAverageOfEmployees());
    }

    @Override
    public String getName() {
        return "average_of_employees_count";
    }

    @Override
    public String getDescription() {
        return "show average of employees count for all elements from collection";
    }
}
