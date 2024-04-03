package managers.commands;

import managers.Receiver;
import recources.Organization;
import system.Request;

/**
 * Данная команда добавляет новый элемент по ключу
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @see Organization
 * @since 1.0
 */
public class InsertCommand implements BaseCommand {
    @Override
    public String execute(Request request) {
        try {
            return Receiver.insertNewElem(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "insert null {element}";
    }

    @Override
    public String getDescription() {
        return "insert new element";
    }
}
