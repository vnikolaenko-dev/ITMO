package managers.commands;

import managers.CollectionManager;

/**
 * Данная команда очищает коллекцию
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class ClearCommand implements BaseCommand {

    @Override
    public void execute(String[] args) throws Exception {
        CollectionManager.getTable().clear();
        System.out.println("Table is clear");
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "clear data from table";
    }
}
