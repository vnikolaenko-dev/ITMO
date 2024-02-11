package managers.commands;

import managers.CommandManager;
import system.TextColor;

import java.util.LinkedHashMap;

public class HelpCommand implements BaseCommand{
    @Override
    public void execute(String[] args) {
        CommandManager commandManager = new CommandManager();
        LinkedHashMap<String, BaseCommand> commandList = commandManager.getCommandList();
        for (String name: commandList.keySet()){
            BaseCommand command = commandList.get(name);
            System.out.println(TextColor.ANSI_BLUE + command.getName() + TextColor.ANSI_RESET + " - " + command.getDescription());
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
