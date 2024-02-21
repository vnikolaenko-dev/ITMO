package managers.commands;


/**
 * Данная команда завершает программу без сохранения коллекции
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class ExitCommand implements BaseCommand {
    @Override
    public void execute(String[] args) throws Exception {
        System.exit(1);
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "close command without save";
    }
}
