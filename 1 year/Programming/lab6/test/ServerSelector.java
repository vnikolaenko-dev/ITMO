import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerSelector {
    public static void main(String[] args) throws IOException {
        int port = 686;

        InetSocketAddress address = new InetSocketAddress(port); // создаем адрес сокета (IP-адрес и порт)

        ServerSocketChannel channel = ServerSocketChannel.open(); // канал для сервера, который слушает порты и создает сокеты для клиентов
        channel.bind(address); // теперь канал слушает по определенному сокету
        channel.configureBlocking(false); // неблокирующий режим

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select(); // количество ключей, чьи каналы готовы к операции. БЛОКИРУЕТ, ПОКА НЕ БУДЕТ КЛЮЧЕЙ
            Set<SelectionKey> selectedKeys = selector.selectedKeys(); // получаем список ключей от каналов, готовых к работеwhile (iter.hasNext()) {
            Iterator<SelectionKey> iter = selectedKeys.iterator(); // получаем итератор ключей

            while (iter.hasNext()) {

                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    System.out.println("new client");

                    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel(); // используется для доступа к серверному каналу
                    SocketChannel client = serverChannel.accept(); // позволяет вашему серверу принять новое входящее соединение и дает вам возможность взаимодействовать с клиентом, используя этот SocketChannel

                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    System.out.println("Reading...");

                    SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
                    client.configureBlocking(false);

                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int bytesRead = client.read(buffer);
                    if (bytesRead != 0) {
                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        String message = new String(bytes);
                        System.out.println(message);
                    }

                    /*
                    InputStream input = client.socket().getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    System.out.println(reader.readLine());
                    */

                    client.register(selector, SelectionKey.OP_WRITE);
                } else if (key.isWritable()) {
                    System.out.println("Writing...");

                    SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
                    client.configureBlocking(false);
                    client.close();
                    client.register(selector, SelectionKey.OP_READ);
                }
                iter.remove();
            }


        }
    }
}
