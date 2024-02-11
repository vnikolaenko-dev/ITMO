package managers;

import exceptions.UnknownCommandException;
import managers.commands.*;

import java.util.LinkedHashMap;

public class CommandManager {
    private static LinkedHashMap<String, BaseCommand> commandList;

    public CommandManager() {
        commandList = new LinkedHashMap<>();
        commandList.put("help", new HelpCommand());
        commandList.put("info", new InfoCommand());
        commandList.put("show", new ShowCommand());
        commandList.put("insert", new InsertCommand());
        commandList.put("update", new UpdateCommand());
        commandList.put("remove_key", new RemoveCommand());
        commandList.put("clear", new ClearCommand());
        commandList.put("read", new ReadXMLCommand());
        commandList.put("save", new SaveXMLCommand());
        commandList.put("exit", new ExitCommand());
    }

    public void startExecuting(String line) throws Exception {
        String commandName = line.split(" ")[0];
        if (!commandList.containsKey(commandName)) {
            throw new UnknownCommandException(commandName);
        }
        BaseCommand command = commandList.get(commandName);
        command.execute(line.split(" "));
    }

    public LinkedHashMap<String, BaseCommand> getCommandList() {
        return commandList;
    }
}
