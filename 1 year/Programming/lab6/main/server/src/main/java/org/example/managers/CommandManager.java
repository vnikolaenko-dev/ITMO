package org.example.managers;

import org.example.exceptions.UnknownCommandException;
import org.example.managers.commands.*;
import org.example.system.Request;

import java.util.ArrayDeque;
import java.util.LinkedHashMap;

/**
 * Данный класс обеспечивает связь между командами и CollectionManager
 *
 * @see CollectionManager
 * @author vnikolaenko
 * @since 1.0
 */
public class CommandManager {
    private static LinkedHashMap<String, BaseCommand> commandList;
    public static ArrayDeque<BaseCommand> lastSixCommand = new ArrayDeque<>();

    public CommandManager() {
        commandList = new LinkedHashMap<>();
        commandList.put("help", new HelpCommand());
        commandList.put("info", new InfoCommand());
        commandList.put("show", new ShowCommand());
        commandList.put("insert", new InsertCommand());
        commandList.put("update", new UpdateCommand());
        commandList.put("remove_key", new RemoveCommand());
        commandList.put("clear", new ClearCommand());
        commandList.put("save", new SaveXMLCommand());
        commandList.put("exit", new ExitCommand());
        commandList.put("remove_greater", new RemoveGraterCommand());
        commandList.put("history", new HistoryCommand());
        commandList.put("replace_if_grater", new ReplaceIfGreaterCommand());
        commandList.put("average_of_employees_count", new AverageEmpCommand());
        commandList.put("min_by_id", new ShowMinByIdCommand());
        commandList.put("print_descending", new PrintDescendingCommand());
    }

    private static String startExecuting(Request request) throws Exception {
        String commandName = request.getMessage().split(" ")[0];
        if (!commandList.containsKey(commandName)) {
            throw new UnknownCommandException(commandName);
        }
        BaseCommand command = commandList.get(commandName);
        String message = command.execute(request);
        if (!(lastSixCommand == null) && lastSixCommand.size() == 6) {
            lastSixCommand.pop();
            lastSixCommand.addLast(command);
        } else {
            assert lastSixCommand != null;
            lastSixCommand.addFirst(command);
        }
        return message;
    }

    public static String startExecutingClientMode(Request request) {
        try {
            if (!request.getMessage().equals("exit") && !request.getMessage().equals("save")){
                return startExecuting(request);
            }
            return "Unknown command";
        } catch (Exception e){
            return e.getMessage();
        }
    }

    public static void startExecutingServerMode(Request request) {
        try {
            if (request.getMessage().equals("exit") || request.getMessage().equals("save")){
                startExecuting(request);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static LinkedHashMap<String, BaseCommand> getCommandList() {
        return commandList;
    }
}
