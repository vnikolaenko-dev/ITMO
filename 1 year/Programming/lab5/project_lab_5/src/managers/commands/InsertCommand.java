package managers.commands;

import data.Organization;
import exceptions.BuildObjectException;
import exceptions.WrongArgumentException;
import managers.CollectionManager;
import data.generators.OrganizationGenerator;
import system.TextColor;

public class InsertCommand implements BaseCommand {
    @Override
    public void execute(String[] args) {
        System.out.println(TextColor.ANSI_BLUE + "Start executing command..." + TextColor.ANSI_RESET);
        try {
            Object organization = OrganizationGenerator.createOrganization(0L);
            CollectionManager.add(args[1], (Organization) organization);
            System.out.println(TextColor.ANSI_BLUE + "Element was added" + TextColor.ANSI_RESET);
        } catch (WrongArgumentException e) {
            System.out.println(TextColor.ANSI_RED + e.getMessage() + TextColor.ANSI_RESET);
        } catch (BuildObjectException e) {
            System.out.println(TextColor.ANSI_RED + e.getMessage() + TextColor.ANSI_RESET);
            System.out.println("Program was returned to safe state");
        }
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getDescription() {
        return "insert new element (null {element})";
    }
}
