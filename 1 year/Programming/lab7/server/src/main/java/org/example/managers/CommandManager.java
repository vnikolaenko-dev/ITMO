package org.example.managers;

import org.example.exceptions.UnknownCommandException;
import org.example.managers.commands.*;
import org.example.system.Request;
import org.example.system.Server;

import java.nio.channels.SocketChannel;
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
    private static LinkedHashMap<String, BaseCommand> commandList = new LinkedHashMap<>();
    private static ArrayDeque<BaseCommand> lastSixCommand = new ArrayDeque<>();
    private CommandManager() {
    }

    public static String startExecuting(Request request) throws Exception {
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

    public static String startExecutingClientMode(Request request, String login) {
        try {
            if (Server.userIsRegistered(login) && DataBaseManager.getUserId(login) != -1){
                if (!request.getMessage().equals("exit") && !request.getMessage().equals("save")){
                    return startExecuting(request);
                }
                return "Unknown command";}
            else {
                if (request.getMessage().split(" ")[0].equals("reg") || request.getMessage().split(" ")[0].equals("log")){
                    return startExecuting(request);
                }  else{
                    Server.removeUser(login);
                    return "please login or register";
                }
            }
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

    public static void setCommandList(LinkedHashMap<String, BaseCommand> commandList) {
        CommandManager.commandList = commandList;
    }

    public static ArrayDeque<BaseCommand> getLastSixCommand() {
        return lastSixCommand;
    }

    public static void setLastSixCommand(ArrayDeque<BaseCommand> lastSixCommand) {
        CommandManager.lastSixCommand = lastSixCommand;
    }

    public static void addCommand(String key, BaseCommand command) {
        commandList.put(key, command);
    }
}
