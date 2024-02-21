package managers.commands;

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
    public void execute(String[] args) throws Exception {
        String[] sp = new String[6];
        int n = 0;
        for (BaseCommand command : CommandManager.lastSixCommand) {
            sp[n] = command.getName();
            n += 1;
        }
        for (int i = 0; i < 6; i++) {
            if (!(sp[i] == null)) {
                System.out.println("-" + sp[i]);
            }
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
