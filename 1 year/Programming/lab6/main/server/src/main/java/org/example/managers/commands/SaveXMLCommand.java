package org.example.managers.commands;

import org.example.managers.Receiver;
import org.example.system.Request;


/**
 * Данная команда сохраняет коллекцию в формате XML
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class SaveXMLCommand implements BaseCommand {
    @Override
    public String execute(Request request) {
        try {
            return Receiver.saveData(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "save data to XML file (path)";
    }
}
