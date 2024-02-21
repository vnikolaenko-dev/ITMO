package managers.commands;

import data.Organization;
import exceptions.BuildOrganizationException;
import exceptions.WrongArgumentException;
import managers.CollectionManager;
import data.generators.OrganizationGenerator;
import system.TextColor;
/**
 * Данная команда добавляет новый элемент по ключу
 *
 * @see BaseCommand
 * @see Organization
 * @author vnikolaenko
 * @since 1.0
 */
public class InsertCommand implements BaseCommand {
    @Override
    public void execute(String[] args) throws WrongArgumentException {
        System.out.println(TextColor.ANSI_BLUE + "Start executing command..." + TextColor.ANSI_RESET);
        if (args.length == 2 && !CollectionManager.getTable().containsKey(args[1])) {
            try {
                Object organization = OrganizationGenerator.createOrganization(0L);
                CollectionManager.add(args[1], (Organization) organization);
                System.out.println(TextColor.ANSI_BLUE + "Element was added" + TextColor.ANSI_RESET);
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            } catch (BuildOrganizationException e) {
                System.out.println(e.getMessage());
                System.out.println("Program was returned to safe state");
            }
        } else throw new WrongArgumentException("KEY");
    }

    @Override
    public String getName() {
        return "insert null {element}";
    }

    @Override
    public String getDescription() {
        return "insert new element";
    }
}
