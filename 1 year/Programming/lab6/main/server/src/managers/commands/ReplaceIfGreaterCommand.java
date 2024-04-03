package managers.commands;

import managers.Receiver;
import system.Request;

/**
 * Данная команда заменяет значение по ключу, если новое значение больше старого
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class ReplaceIfGreaterCommand implements BaseCommand {
    @Override
    public String execute(Request request) {
        try {
            return Receiver.replaceIfGreater(request);
        } catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "replace_if_greater";
    }

    @Override
    public String getDescription() {
        return "null {element} - update element by key if new bigger than element in collection with the same key";
    }
}
