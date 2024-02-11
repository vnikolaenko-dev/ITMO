package managers.commands;

import system.TextColor;
import system.WriterXML;

public class SaveXMLCommand implements BaseCommand{
    @Override
    public void execute(String[] args) throws Exception {
        WriterXML.write(args[1]);
        System.out.println(TextColor.ANSI_BLUE + "Data was saved to:" + TextColor.ANSI_RESET + "\n" + args[1]);
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
