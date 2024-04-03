package managers.commands;

import managers.Receiver;
import system.Request;


/**
 * Данная команда удаляет из коллекции все элементы, превышающие заданный
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class RemoveGraterCommand implements BaseCommand {
    @Override
    public String execute(Request request) {
        try {
            return Receiver.removeGreater(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "remove_grater {element}";
    }

    @Override
    public String getDescription() {
        return "remove elements with grater than the element";
    }
}
