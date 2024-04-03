package managers.commands;

import managers.Receiver;
import system.Request;

/**
 * Данная команда выводит данные о программе
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class InfoCommand implements BaseCommand {
    @Override
    public String execute(Request request) {
        try {
            return Receiver.getInfo(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "information about app";
    }
}
