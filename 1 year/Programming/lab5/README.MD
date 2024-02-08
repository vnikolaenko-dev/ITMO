Мануал к 5 лабораторной работе
------------------------------
Принцип файловой системы и классов
-----------------------------------

Полезные ссылки:
https://javarush.com/groups/posts/4133-kofe-breyk-233-shablon-proektirovanija-command-komanda-8-samihkh-kaverznihkh-voprosov-po-java-d

Шаблон Command из статьи

Моя структура по данному шаблону:
BaseCommand 
Concrete Command
Receiver - CollectionManager
Invoker - CommandManager
Client - Console

Managers:

- Console - обработчик ввода команд
	постоянно ожидает ввод команд - ввод перенаправляется в CommandManager

- CommandManager - хранит CommandList HashMap<String, BaseCommand> со всеми командами
	обесечивает связь между Console и всеми коммандами
	исполнение обеспечивает метод startExecuting(String[] args)

- CollectionManager - хранит static Hashtable (зависит от варианта)
	работа с коллекцией
---------------------------------------------------------------------------------------
Commands:

Все комманды наследуются от BaseCommand:
	execute, getName, getDescription
----------------------------------------
Принцип работы:

Console ожидает ввода
Console парсит строку и кидает аргументы в CommandManager (
	input: insert element -> CommandManager.startExecuting("insert element".split(" "))
CommandManager определяет комманду и ключи String args: (insert - command, element - key)
CommandManager получаем нужную нам команду и запускаем ее со всеми аргументами CommandList.get(command).execute(args)
Далее каждая команда выполняется так, как вы пропишите
---------------------------------------------------------------------------------------------------------------------
System:

InputValidator - проверяет корректность введенных данных
IdGenerator - генерирует уникальные ID
OrganizationGenerator - генерирует объекты
 
