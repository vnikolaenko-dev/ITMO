package org.example.managers.commands;

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
            return Receiver.getHistory(request);
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
