package managers.commands;

import system.ReaderXML;

public class ReadXMLCommand implements BaseCommand {
    @Override
    public void execute(String[] args) throws Exception {
        ReaderXML.read(args[1]);
    }

    @Override
    public String getName() {
        return "read";
    }

    @Override
    public String getDescription() {
        return "read data from XML file (path)";
    }
}
