package org.example.system;

import org.example.exceptions.ReadRequestException;
import org.example.exceptions.RootException;
import org.example.filelogic.ReaderXML;
import org.example.managers.CommandManager;
import org.example.recources.Organization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

import static org.example.system.Main.LOGGER;

public class Server {
    private InetSocketAddress address;
    private ServerSocketChannel chanel;
    private File file;
    public static String data_path = null;
    private Selector selector;

    public void initialize(int port, String filePath) throws IOException, RootException {
        this.address = new InetSocketAddress(port); // создаем адрес сокета (IP-адрес и порт)
        this.chanel = ServerSocketChannel.open();
        this.chanel.bind(address);
        this.chanel.configureBlocking(false); // неблокирующий режим ввода-вывода
        this.selector = Selector.open();
        this.file = new File(filePath);

        if (file.canRead() && file.canWrite()) {
            new CommandManager();
            try {
                LOGGER.info("Downloading data from file...");
                ReaderXML.read(filePath);
                data_path = filePath;
                LOGGER.info("Data was downloaded");
            } catch (Exception e) {
                LOGGER.warn("Error while reading file\n" + filePath);
                System.exit(0);
            }
        } else {
            LOGGER.warn("You do not have enough root to write or read file");
            throw new RootException("You do not have enough root to write or read file");
        }
        LOGGER.info("Server is initialized");
    }

    public void start() {
        LOGGER.info("Server is available");
        try {
            new Thread(() -> {
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    try {
                        String input = consoleReader.readLine();
                        if (input.equals("exit") || input.equals("save")){
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

            String message = null;
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
                            Request request;
                            SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
                            try {
                                request = readRequest(key);
                                message = CommandManager.startExecutingClientMode(request);
                                SelectionKey keyNew = client.register(selector, SelectionKey.OP_WRITE);
                                request = new Request(message, new Organization(), null);
                                keyNew.attach(request);
                            }
                            catch (ReadRequestException e){
                                message = "Something wrong with request or unknown command";
                                request = new Request(message, new Organization(), null);
                                key.attach(request);
                                sendAnswer(key);
                                key.cancel();
                            }

                        } else if (key.isWritable()) {
                            sendAnswer(key);
                            SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
                            client.register(selector, SelectionKey.OP_READ);
                        }
                        iter.remove();

                    } catch (IOException e) {
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

        } catch (Exception e) {
            LOGGER.warn("Something wrong with server\n" + e.getMessage());
            System.exit(0);
        }
    }

    private Request readRequest(SelectionKey key) throws IOException, ReadRequestException {
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
            LOGGER.info("Request from " + client.socket());
            ByteArrayInputStream bi = new ByteArrayInputStream(bigBuffer.array());
            ObjectInputStream oi = new ObjectInputStream(bi);
            return (Request) oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.debug("Something wrong with request from " + client.socket());
            throw new ReadRequestException();
        }
    }

    public void sendAnswer(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
        client.configureBlocking(false);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(key.attachment());
        objectOutputStream.close();
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

        // Отправляем данные
        while (buffer.hasRemaining()) {
            client.write(buffer);
        }
        LOGGER.info("Answer was sent to " + client.socket().toString());
    }

}
