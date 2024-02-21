package managers.commands;

import system.ReaderXML;

import javax.xml.stream.FactoryConfigurationError;

public class ReadXMLCommand implements BaseCommand {
    @Override
    public void execute(String[] args) throws Exception {
        ReaderXML.read(args[1], false);
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
