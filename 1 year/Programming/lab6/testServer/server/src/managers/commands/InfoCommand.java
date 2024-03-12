package managers.commands;

import exceptions.WrongArgumentException;
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
    public String execute(String[] args) throws Exception {
        StringBuilder text = new StringBuilder("");
        if (args.length == 1) {
            text.append("Data type - " + CollectionManager.getTable().getClass().getName());
            text.append("\nCount of organization - " + CollectionManager.getTable().keySet().size());
            text.append("\nInit date - " + CollectionManager.getInitDate());
            return text.toString();
        } else{
            throw new WrongArgumentException("command parameter");
        }
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
