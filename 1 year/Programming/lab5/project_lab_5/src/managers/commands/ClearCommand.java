package managers.commands;

import managers.CollectionManager;

public class ClearCommand implements BaseCommand {
    @Override
    public void execute(String[] args) {
        CollectionManager.getTable().clear();
        System.out.println("Table is clear");
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "clear data from table";
    }
}
