package org.example.managers.commands;

import org.example.exceptions.WrongArgumentException;
import org.example.managers.Receiver;
import org.example.system.Request;

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
            if (request.getMessage().split(" ").length == 1) {
                return Receiver.clear(request);
            } else throw new WrongArgumentException("command parameter");
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
