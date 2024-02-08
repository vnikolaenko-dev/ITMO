package managers.commands;

public interface BaseCommand {
    public void execute(String[] args);

    public String getName();

    public String getDescription();
}
