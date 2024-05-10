package org.example.system;

import org.example.exceptions.ReadRequestException;
import org.example.exceptions.RootException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.*;
import org.example.managers.DataBaseManager;
import org.example.managers.Receiver;
import org.example.managers.commands.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

import static org.example.system.Main.LOGGER;

public class Server {
    private InetSocketAddress address;
    private ServerSocketChannel chanel;
    private ServerSocket serverSocket;
    private Selector selector;
    private static Set<String> registered = new HashSet<>();
    private Request request;

    public void initialize(int port) throws IOException, RootException {
        this.address = new InetSocketAddress(port); // создаем адрес сокета (IP-адрес и порт)
        this.chanel = ServerSocketChannel.open();
        this.chanel.bind(address);
        this.chanel.configureBlocking(false); // неблокирующий режим ввода-вывода
        this.selector = Selector.open();
        LOGGER.info("Server is initialized");

        CommandManager.addCommand("help", new HelpCommand());
        CommandManager.addCommand("info", new InfoCommand());
        CommandManager.addCommand("show", new ShowCommand());
        CommandManager.addCommand("insert", new InsertCommand());
        CommandManager.addCommand("update", new UpdateCommand());
        CommandManager.addCommand("remove_key", new RemoveCommand());
        CommandManager.addCommand("clear", new ClearCommand());
        CommandManager.addCommand("exit", new ExitCommand());
        CommandManager.addCommand("remove_grater", new RemoveGraterCommand());
        CommandManager.addCommand("history", new HistoryCommand());
        CommandManager.addCommand("replace_if_grater", new ReplaceIfGreaterCommand());
        CommandManager.addCommand("average_of_employees_count", new AverageEmpCommand());
        CommandManager.addCommand("min_by_id", new ShowMinByIdCommand());
        CommandManager.addCommand("print_descending", new PrintDescendingCommand());
        CommandManager.addCommand("reg", new RegisterCommand());
        CommandManager.addCommand("log", new LoginCommand());
    }

    public void start() {
        // LOGGER.info("Server is available");
        CollectionManager.setTable(DataBaseManager.getDataFromDatabase());
        try {
            new Thread(() -> {
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    try {
                        String input = consoleReader.readLine();
                        if (input.equals("exit") || input.equals("save")) {
                            CommandManager.startExecutingServerMode(new Request(input, null, null));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start(); // поток для ввода команд на сервере
            chanel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select(); // Количество ключей, чьи каналы готовы к операции. БЛОКИРУЕТ, ПОКА НЕ БУДЕТ КЛЮЧЕЙ
                Set<SelectionKey> selectedKeys = selector.selectedKeys(); // получаем список ключей от каналов, готовых к работеwhile (iter.hasNext()) {
                Iterator<SelectionKey> iter = selectedKeys.iterator(); // получаем итератор ключей
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    try {
                        if (key.isAcceptable()) {
                            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel(); // используется для доступа к серверному каналу
                            SocketChannel client = serverChannel.accept(); // позволяет вашему серверу принять новое входящее соединение и дает вам возможность взаимодействовать с клиентом, используя этот SocketChannel
                            LOGGER.info("new client connected:" + client.socket().toString());
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {

                            SocketChannel client = (SocketChannel) key.channel();
                            client.configureBlocking(false);

                            Thread getRequestThread = new Thread(() -> {
                                try {
                                    readRequest(key);
                                } catch (IOException | ReadRequestException e) {
                                    String message = "Something wrong with request or unknown command";
                                    request = new Request(message, new Organization(), null);
                                    key.attach(request);
                                    key.cancel();
                                }
                            });
                            getRequestThread.start();
                            getRequestThread.join();

                            Thread processRequestThread = new Thread(() -> {
                                Request re = (Request) key.attachment();
                                request = doCommand(re);
                            });
                            processRequestThread.start();
                            processRequestThread.join();

                            SelectionKey keyNew = client.register(selector, SelectionKey.OP_WRITE);
                            keyNew.attach(request);

                        } else if (key.isWritable()) {
                            SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
                            client.configureBlocking(false);

                            Thread sendThread = new Thread(() -> {
                                try {
                                    sendAnswer(client, key);
                                } catch (SocketException e) {
                                    LOGGER.info("Connection is off by " + key.channel().toString());
                                    key.cancel();
                                } catch (IOException e) {
                                    LOGGER.error("UNKNOWN ERROR");
                                    e.printStackTrace();
                                }
                            });
                            sendThread.start();
                            sendThread.join();
                            client.register(selector, SelectionKey.OP_READ);
                        }
                        iter.remove();

                    } catch (IOException | InterruptedException e) {
                        if (e.getMessage().equals("Connection reset")) {
                            SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
                            LOGGER.info("The connection was reset by the client: " + client.socket());
                            key.cancel();
                        } else {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e){
            start();
        }
}
    private synchronized Request doCommand(Request re){
        // LOGGER.info("Execute command from " + re.getLogin());
        System.out.println("ready");
        return new Request(CommandManager.startExecutingClientMode(re, re.getLogin()), null, null);
    }

    private void readRequest(SelectionKey key) throws IOException, ReadRequestException {
        SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
        client.configureBlocking(false);

        ArrayList<ByteBuffer> bufferList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            client.read(buffer);
            if (buffer.limit() == buffer.position() || bufferList.isEmpty()) {
                bufferList.add(buffer);
            } else {
                break;
            }
        }
        ByteBuffer bigBuffer = ByteBuffer.allocate(bufferList.size() * 8192);
        for (ByteBuffer byteBuffer : bufferList) {
            bigBuffer.put(byteBuffer.array());
        }

        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bigBuffer.array());
            ObjectInputStream oi = new ObjectInputStream(bi);
            key.attach((Request) oi.readObject());
            LOGGER.info("Request from " + client.socket());
        } catch (IOException | ClassNotFoundException e) {
            return;
        }
    }

    public void sendAnswer(SocketChannel clientChannel, SelectionKey key) throws IOException {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
             ObjectOutputStream clientDataOut = new ObjectOutputStream(bytes)) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(key.attachment());
            objectOutputStream.close();
            ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

            // Отправляем данные
            while (buffer.hasRemaining()) {
                clientChannel.write(buffer);
            }

            LOGGER.info("Answer was sent to " + clientChannel.socket().toString());
        }
    }

    public static void addUser(String login) {
        registered.add(login);
    }

    public static void removeUser(String login) {
        registered.remove(login);
    }

    public static boolean userIsRegistered(String login) {
        return registered.contains(login);
    }
}
