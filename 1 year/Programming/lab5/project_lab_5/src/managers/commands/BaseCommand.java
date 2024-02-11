package managers.commands;

public interface BaseCommand {
    public void execute(String[] args) throws Exception;

    public String getName();

    public String getDescription();
}
