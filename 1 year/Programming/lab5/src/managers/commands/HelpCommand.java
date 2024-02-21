package managers.commands;

import managers.CommandManager;
import system.TextColor;

import java.util.LinkedHashMap;
/**
 * Данная команда выводит описание всех команд
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class HelpCommand implements BaseCommand{
    @Override
    public void execute(String[] args) throws Exception{
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
