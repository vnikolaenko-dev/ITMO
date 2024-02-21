package managers.commands;

import exceptions.NoElementException;
import managers.CollectionManager;
import system.TextColor;
/**
 * Данная команда удаляет из коллекции элемент по ключу
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class RemoveCommand implements BaseCommand {
    @Override
    public void execute(String[] args) throws Exception{
        System.out.println(TextColor.ANSI_BLUE + "Start executing command..." + TextColor.ANSI_RESET);
        try {
            CollectionManager.remove(args[1]);
            System.out.println(TextColor.ANSI_BLUE + "Element was removed" + TextColor.ANSI_RESET);
        } catch (NoElementException e) {
            System.out.println(TextColor.ANSI_RED + e.getMessage() + TextColor.ANSI_RESET);
            System.out.println("Program was returned to safe state");
        }
    }

    @Override
    public String getName() {
        return "remove_key null";
    }

    @Override
    public String getDescription() {
        return "remove_key element by key";
    }
}
