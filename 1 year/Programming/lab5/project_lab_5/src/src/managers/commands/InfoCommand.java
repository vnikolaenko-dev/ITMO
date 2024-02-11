package managers.commands;

import managers.CollectionManager;


public class InfoCommand implements BaseCommand {
    @Override
    public void execute(String[] args) {
        System.out.println("Data type - " + CollectionManager.getTable().getClass().getName());
        System.out.println("Count of organization - " + CollectionManager.getTable().keySet().size());
        System.out.println("Init date - " + CollectionManager.getInitDate());
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
