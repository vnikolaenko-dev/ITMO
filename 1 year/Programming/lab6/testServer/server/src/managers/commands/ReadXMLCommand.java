package managers.commands;

import filelogic.ReaderXML;
import managers.Console;
import managers.FileManager;

public class ReadXMLCommand implements BaseCommand {
    @Override
    public String execute(String[] args) throws Exception {
        FileManager.readFromPath(Console.data_path);
        return "pop";
    }

    @Override
    public String getName() {
        return "read (path)";
    }

    @Override
    public String getDescription() {
        return "read data from XML file";
    }
}
