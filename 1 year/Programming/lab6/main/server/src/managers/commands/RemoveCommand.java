package managers.commands;

import managers.Receiver;
import system.Request;

/**
 * Данная команда удаляет из коллекции элемент по ключу
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class RemoveCommand implements BaseCommand {
    @Override
    public String execute(Request request) {
        try {
            return Receiver.removeByKey(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "remove_key null";
    }

    @Override
    public String getDescription() {
        return "remove_key element by key";
    }
}
