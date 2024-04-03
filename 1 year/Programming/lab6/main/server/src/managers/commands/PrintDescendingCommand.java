package managers.commands;

import managers.Receiver;
import system.Request;


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
            return Receiver.getDescending(request);
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
