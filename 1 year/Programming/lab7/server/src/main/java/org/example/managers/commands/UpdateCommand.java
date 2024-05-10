package org.example.managers.commands;

import org.example.managers.Receiver;
import org.example.system.Request;

/**
 * Данная команда обновляет элемент коллекции по ID
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class UpdateCommand implements BaseCommand {
    @Override
    public String execute(Request request) {
        try {
            return Receiver.updateById(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "update id {element}";
    }

    @Override
    public String getDescription() {
        return "update element";
    }
}
