import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) throws IOException {
        if (args.length != 2){
            System.out.println("Wrong argument");
            return;
        }
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        InetSocketAddress address = new InetSocketAddress(port); // создаем адрес сокета (IP-адрес и порт)
        SocketChannel socket = SocketChannel.open();
        socket.connect(address);
        socket.configureBlocking(false); // неблокирующий режим ввода-вывода

        String message = "Hello, World!";

        ByteBuffer buf = ByteBuffer.wrap(message.getBytes());
        socket.write(buf);
    }
}
