package managers.commands;

import exceptions.WrongArgumentException;
import managers.CommandManager;

/**
 * Данная команда выводит последние 6 команд без ключей
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class HistoryCommand implements BaseCommand {
    @Override
    public String execute(String[] args) throws Exception {
        StringBuilder text = new StringBuilder("");
        if (args.length == 1) {
            String[] sp = new String[6];
            int n = 0;
            for (BaseCommand command : CommandManager.lastSixCommand) {
                sp[n] = command.getName();
                n += 1;
            }
            for (int i = 0; i < 6; i++) {
                if (!(sp[i] == null)) {
                    text.append("-" + sp[i]);
                }
            }
            return text.toString();
        } else{
            throw new WrongArgumentException("command parameter");
        }
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getDescription() {
        return "show last 6 command";
    }
}
