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
    public String execute(String[] args) throws NoElementException {
        System.out.println(TextColor.ANSI_BLUE + "Start executing command..." + TextColor.ANSI_RESET);
        CollectionManager.remove(args[1]);
        return "Element was removed";
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
