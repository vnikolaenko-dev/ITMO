package managers.commands;

import data.Organization;
import exceptions.BuildObjectException;
import exceptions.NoElementException;
import exceptions.ReplayIdException;
import exceptions.WrongArgumentException;
import managers.CollectionManager;
import data.generators.OrganizationGenerator;
import system.TextColor;

public class UpdateCommand implements BaseCommand {
    @Override
    public void execute(String[] args) {
        System.out.println(TextColor.ANSI_BLUE + "Start executing command..." + TextColor.ANSI_RESET);
        try {
            boolean elementInCollection = false;
            Long id = Long.parseLong(args[1]);

            for (String key : CollectionManager.getTable().keySet()) {
                if (CollectionManager.getTable().get(key).getId().equals(id)) {
                    elementInCollection = true;
                    Object organization = OrganizationGenerator.createOrganization(id);
                    if (organization instanceof Organization) {
                        CollectionManager.remove(key);
                        CollectionManager.add(args[0], (Organization) organization);
                    }
                    System.out.println(TextColor.ANSI_BLUE + "Element was updated" + TextColor.ANSI_RESET);
                }
            }
            if (!elementInCollection) {
                throw new NoElementException(id);
            }
        } catch (WrongArgumentException | BuildObjectException | NoElementException e) {
            System.out.println(e.getMessage());
            System.out.println("Program was returned to safe state");
        }
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "update element (id {element})";
    }
}
