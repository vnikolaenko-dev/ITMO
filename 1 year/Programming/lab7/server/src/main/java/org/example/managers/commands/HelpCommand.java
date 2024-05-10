package org.example.managers.commands;

import org.example.exceptions.WrongArgumentException;
import org.example.managers.Receiver;
import org.example.system.Request;

/**
 * Данная команда выводит описание всех команд
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class HelpCommand implements BaseCommand {
    @Override
    public String execute(Request request) {
        try {
            if (request.getMessage().split(" ").length == 1) {
                return Receiver.getHelp();
            } else throw new WrongArgumentException("command parameter");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "use this command to see information";
    }
}
