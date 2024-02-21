package managers.commands;

/**
 * Базовый интерфейс для реализации команд. Такие команды содержаться в CollectionManager
 *
 * @author vnikolaenko
 * @see managers.CollectionManager
 * @since 1.0
 */
public interface BaseCommand {
    /**
     * Базовый метод для вывода исполнения команды
     * Выбрасывает ошибки при реализации
     *
     * @author vnikolaenko
     * @since 1.0
     */
    public void execute(String[] args) throws Exception;

    /**
     * Базовый метод для вывода названия команды
     *
     * @author vnikolaenko
     * @since 1.0
     */
    public String getName();

    /**
     * Базовый метод для вывода описания команды
     *
     * @author vnikolaenko
     * @since 1.0
     */
    public String getDescription();
}
