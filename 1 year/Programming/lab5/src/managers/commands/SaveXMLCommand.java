package managers.commands;

import managers.CollectionManager;
import managers.Console;
import system.TextColor;
import system.WriterXML;

/**
 * Данная команда сохраняет коллекцию в формате XML
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class SaveXMLCommand implements BaseCommand {
    @Override
    public void execute(String[] args) throws Exception {
        WriterXML.write(Console.data_path);
        System.out.println(TextColor.ANSI_BLUE + "Data was saved to:" + TextColor.ANSI_RESET + "\n" + Console.data_path);
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
