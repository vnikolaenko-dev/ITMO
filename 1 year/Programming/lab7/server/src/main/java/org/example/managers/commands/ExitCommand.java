package org.example.managers.commands;


import org.example.exceptions.WrongArgumentException;
import org.example.managers.Receiver;
import org.example.system.Request;

/**
 * Данная команда завершает программу без сохранения коллекции
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class ExitCommand implements BaseCommand {
    @Override
    public String execute(Request request) {
        try {
            if (request.getMessage().split(" ").length == 1) {
                return Receiver.exit();
            } else throw new WrongArgumentException("command parameter");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "close command without save";
    }
}
