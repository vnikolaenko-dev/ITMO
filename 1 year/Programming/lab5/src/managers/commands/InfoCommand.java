package managers.commands;

import data.Organization;
import managers.CollectionManager;

/**
 * Данная команда выводит данные о программе
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class InfoCommand implements BaseCommand {
    @Override
    public void execute(String[] args) throws Exception {
        System.out.println("Data type - " + CollectionManager.getTable().getClass().getName());
        System.out.println("Count of organization - " + CollectionManager.getTable().keySet().size());
        System.out.println("Init date - " + CollectionManager.getInitDate());
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "information about app";
    }
}
