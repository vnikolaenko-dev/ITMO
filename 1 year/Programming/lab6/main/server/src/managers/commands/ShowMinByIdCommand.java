package managers.commands;

import managers.Receiver;
import system.Request;


/**
 * Данная команда выводит элемент с минимальным ID
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class ShowMinByIdCommand implements BaseCommand {
    @Override
    public String execute(Request request) {
        try {
            return Receiver.showMinById(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "min_by_id";
    }

    @Override
    public String getDescription() {
        return "show element with the less ID";
    }
}
