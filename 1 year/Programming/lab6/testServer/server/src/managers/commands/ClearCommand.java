package managers.commands;

import exceptions.WrongArgumentException;
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
    public String execute(String[] args) throws Exception {
        if (args.length == 1) {
            CollectionManager.getTable().clear();
            return "Table is clear";
        } else{
            throw new WrongArgumentException("command parameter");
        }

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
