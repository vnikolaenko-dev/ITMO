package org.example.managers.commands;

import org.example.exceptions.WrongArgumentException;
import org.example.managers.Receiver;
import org.example.system.Request;

/**
 * Данная команда выводит среднее значение поля employeesCount (Organization) для всех элементов коллекции
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class AverageEmpCommand implements BaseCommand {
    @Override
    public String execute(Request request) {
        try {
            if (request.getMessage().split(" ").length == 1) {
                return Receiver.getAverageEmp();
            } else throw new WrongArgumentException("command parameter");
        } catch (Exception e) {
            return e.getMessage();
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
