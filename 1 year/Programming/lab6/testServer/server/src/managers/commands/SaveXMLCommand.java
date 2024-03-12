package managers.commands;

import exceptions.RootException;
import exceptions.WrongArgumentException;
import managers.CollectionManager;
import managers.Console;
import managers.FileManager;
import system.TextColor;
import filelogic.WriterXML;

import java.io.IOException;

/**
 * Данная команда сохраняет коллекцию в формате XML
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class SaveXMLCommand implements BaseCommand {
    @Override
    public String execute(String[] args) throws IOException, RootException, WrongArgumentException {
        FileManager.writeToPath(Console.data_path);
        if (args.length == 1) {
            FileManager.writeToPath(Console.data_path);
        } else{
            throw new WrongArgumentException("command parameter");
        }
        return "Data was saved";
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
