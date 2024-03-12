package managers.commands;

import exceptions.WrongArgumentException;
import recources.Organization;
import managers.CollectionManager;

import java.util.Hashtable;

/**
 * Данная команда выводит список всех команд
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class ShowCommand implements BaseCommand {
    @Override
    public String execute(String[] args) throws Exception {
        StringBuilder text = new StringBuilder("");
        if (args.length == 1) {
            Hashtable<String, Organization> table = CollectionManager.getTable();
            for (String x : table.keySet()) {
                text.append(table.get(x));
            }
            if (table.isEmpty()) {
                text.append(CollectionManager.getTable().getClass().getName() + " is empty");
            }
            return text.toString();
        } else{
            throw new WrongArgumentException("command parameter");
        }

    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "show data";
    }
}
