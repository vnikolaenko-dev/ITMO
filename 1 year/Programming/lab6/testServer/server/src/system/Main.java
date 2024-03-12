package system;

import managers.CommandManager;
import managers.Console;
import recources.Organization;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {

        }
        int port = Integer.parseInt(args[0]);
        InetSocketAddress address = new InetSocketAddress(port); // создаем адрес сокета (IP-адрес и порт)

        ServerSocketChannel channel = ServerSocketChannel.open(); // канал для сервера, который слушает порты и создает сокеты для клиентов
        channel.bind(address); // теперь канал слушает по определенному сокету
        channel.configureBlocking(false); // неблокирующий режим

        Selector selector = Selector.open();
        try {
            channel.register(selector, SelectionKey.OP_ACCEPT);

            File file = new File(args[1]);
            System.out.println(args[1]);
            if (file.canRead() && file.canWrite()) {
                Console console = new Console();
                console.start(args);
            } else {
                System.out.println(TextColor.ANSI_RED + "You do not have enough root" + TextColor.ANSI_RESET);
            }

            String message = null;
            while (true) {
                selector.select(); // количество ключей, чьи каналы готовы к операции. БЛОКИРУЕТ, ПОКА НЕ БУДЕТ КЛЮЧЕЙ
                Set<SelectionKey> selectedKeys = selector.selectedKeys(); // получаем список ключей от каналов, готовых к работеwhile (iter.hasNext()) {
                Iterator<SelectionKey> iter = selectedKeys.iterator(); // получаем итератор ключей

                while (iter.hasNext()) {

                    SelectionKey key = iter.next();
                    try {
                        if (key.isAcceptable()) {
                            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel(); // используется для доступа к серверному каналу
                            SocketChannel client = serverChannel.accept(); // позволяет вашему серверу принять новое входящее соединение и дает вам возможность взаимодействовать с клиентом, используя этот SocketChannel
                            System.out.println("new client: " + client.socket().toString());
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            System.out.println("Reading...");

                            SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
                            client.configureBlocking(false);

                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            client.read(buffer);



                            ByteArrayInputStream bi = new ByteArrayInputStream(buffer.array());
                            ObjectInputStream oi = new ObjectInputStream(bi);
                            Request request = (Request) oi.readObject();
                            message = CommandManager.startExecuting(request.getMessage());


                            client.register(selector, SelectionKey.OP_WRITE);
                        /*
                        InputStream input = client.socket().getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        System.out.println(reader.readLine());
                        */

                        } else if (key.isWritable()) {
                            System.out.println("Writing...");
                            Request request = new Request(message, new Organization());

                            SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
                            client.configureBlocking(false);

                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                            objectOutputStream.writeObject(request);
                            objectOutputStream.close();
                            ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

                            // Отправляем данные
                            while (buffer.hasRemaining()) {
                                client.write(buffer);
                            }

                            client.register(selector, SelectionKey.OP_READ);
                            buffer.clear();
                            System.out.println("Request was sent to " + client.socket().toString());
                        }
                        iter.remove();
                    } catch (IOException e) {
                        if (e instanceof SocketException && e.getMessage().equals("Connection reset")) {
                            System.out.println("Соединение было сброшено клиентом.");
                            System.out.println(e.getMessage());
                            key.cancel();
                        } else {
                            e.printStackTrace();
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Something wrong with server");
        }
    }
}