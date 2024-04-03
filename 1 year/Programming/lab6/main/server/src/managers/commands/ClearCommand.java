package managers.commands;

import managers.Receiver;
import system.Request;

/**
 * Данная команда очищает коллекцию
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class ClearCommand implements BaseCommand {

    @Override
    public String execute(Request request) {
        try {
            return Receiver.clearTable(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "clear data from table";
    }
}
