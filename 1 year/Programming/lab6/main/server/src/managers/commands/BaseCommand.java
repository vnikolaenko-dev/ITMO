package managers.commands;

import system.Request;

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
    String execute(Request request) throws Exception;

    /**
     * Базовый метод для вывода названия команды
     *
     * @author vnikolaenko
     * @since 1.0
     */
    String getName();

    /**
     * Базовый метод для вывода описания команды
     *
     * @author vnikolaenko
     * @since 1.0
     */
    String getDescription();
}
