package managers.commands;

import exceptions.WrongArgumentException;
import managers.CollectionManager;

/**
 * Данная команда выводит среднее значение поля employeesCount (Organization) для всех элементов коллекции
 *
 * @see BaseCommand
 * @see recources.Organization
 * @author vnikolaenko
 * @since 1.0
 */
public class AverageEmpCommand implements BaseCommand {
    @Override
    public String execute(String[] args) throws Exception {
        if (args.length == 1) {
            return "Average of employees count is " + CollectionManager.getAverageOfEmployees();
        } else{
            throw new WrongArgumentException("command parameter");
        }
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
