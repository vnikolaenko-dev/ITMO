package managers.commands;

import managers.Receiver;
import system.Request;


/**
 * Данная команда выводит список всех команд
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class ShowCommand implements BaseCommand {
    @Override
    public String execute(Request request) {
        try {
            return Receiver.showData(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "show data";
    }
}
