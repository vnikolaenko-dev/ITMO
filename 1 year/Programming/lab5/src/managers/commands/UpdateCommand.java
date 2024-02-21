package managers.commands;

import data.Organization;
import exceptions.NoElementException;
import managers.CollectionManager;
import data.generators.OrganizationGenerator;
import system.TextColor;

/**
 * Данная команда обновляет элемент коллекции по ID
 *
 * @see BaseCommand
 * @author vnikolaenko
 * @since 1.0
 */
public class UpdateCommand implements BaseCommand {
    @Override
    public void execute(String[] args) throws Exception {
        System.out.println("Start executing command...");

        boolean elementInCollection = false;
        Long id = Long.parseLong(args[1]);

        for (String key : CollectionManager.getTable().keySet()) {
            if (CollectionManager.getTable().get(key).getId().equals(id)) {
                System.out.println(TextColor.ANSI_BLUE + "Updating element with id " + args[1] + TextColor.ANSI_RESET);
                elementInCollection = true;

                Organization organization = OrganizationGenerator.createOrganization(id);
                CollectionManager.remove(key);
                CollectionManager.add(key, organization);

                System.out.println(TextColor.ANSI_BLUE + "Element was updated" + TextColor.ANSI_RESET);
            }
        }
        if (!elementInCollection) {
            throw new NoElementException(id);
        }
    }

    @Override
    public String getName() {
        return "update id {element}";
    }

    @Override
    public String getDescription() {
        return "update element";
    }
}
