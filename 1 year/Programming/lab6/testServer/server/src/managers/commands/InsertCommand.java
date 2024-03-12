package managers.commands;

import recources.Organization;
import exceptions.BuildOrganizationException;
import exceptions.WrongArgumentException;
import managers.CollectionManager;
import managers.generators.OrganizationGenerator;
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
    public String execute(String[] args) throws WrongArgumentException, BuildOrganizationException {
        StringBuilder text = new StringBuilder("");
        if (args.length == 2 && !CollectionManager.getTable().containsKey(args[1])) {
            Object organization = OrganizationGenerator.createOrganization(0L);
            CollectionManager.add(args[1], (Organization) organization);
            text.append(TextColor.ANSI_BLUE + "Element was added" + TextColor.ANSI_RESET);
            return text.toString();
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
