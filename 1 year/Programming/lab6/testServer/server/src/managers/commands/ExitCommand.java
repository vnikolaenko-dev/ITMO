package managers.commands;


import exceptions.WrongArgumentException;

/**
 * Данная команда завершает программу без сохранения коллекции
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class ExitCommand implements BaseCommand {
    @Override
    public String execute(String[] args) throws Exception {
        if (args.length == 1) {
            System.exit(1);
            return "";
        } else{
            throw new WrongArgumentException("command parameter");
        }
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
