package org.example.managers.commands;

import org.example.exceptions.WrongArgumentException;
import org.example.managers.Receiver;
import org.example.system.Request;

/**
 * Данная команда выводит последние 6 команд без ключей
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class HistoryCommand implements BaseCommand {
    @Override
    public String execute(Request request) throws Exception {
        try {
            if (request.getMessage().split(" ").length == 1) {
                return Receiver.getHistory();
            } else throw new WrongArgumentException("command parameter");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getDescription() {
        return "show last 6 command";
    }
}
