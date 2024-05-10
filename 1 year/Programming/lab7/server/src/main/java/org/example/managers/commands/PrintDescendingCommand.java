package org.example.managers.commands;

import org.example.exceptions.WrongArgumentException;
import org.example.managers.Receiver;
import org.example.system.Request;


/**
 * Данная команда выводит элементы в порядке убывания
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class PrintDescendingCommand implements BaseCommand {
    @Override
    public String execute(Request request) {
        try {
            if (request.getMessage().split(" ").length == 1) {
                return Receiver.getDescending();
            } else throw new WrongArgumentException("command parameter");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "print_descending";
    }

    @Override
    public String getDescription() {
        return "print all element from smaller to bigger";
    }
}
