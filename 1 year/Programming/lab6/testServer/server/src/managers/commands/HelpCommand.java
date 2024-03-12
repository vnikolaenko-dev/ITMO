package managers.commands;

import exceptions.WrongArgumentException;
import managers.CollectionManager;
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
    public String execute(String[] args) throws Exception{
        StringBuilder text = new StringBuilder("");
        if (args.length == 1) {
            LinkedHashMap<String, BaseCommand> commandList = CommandManager.getCommandList();
            for (String name: commandList.keySet()){
                BaseCommand command = commandList.get(name);
                text.append(TextColor.ANSI_BLUE).append(command.getName()).append(TextColor.ANSI_RESET).append(" - ").append(command.getDescription());
            }
            System.out.println(text);
            return text.toString();
        } else{
            throw new WrongArgumentException("command parameter");
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
