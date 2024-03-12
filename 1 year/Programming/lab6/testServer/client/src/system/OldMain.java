package system;

import recources.Organization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class OldMain {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Wrong argument");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        InetSocketAddress address = new InetSocketAddress(port); // создаем адрес сокета (IP-адрес и порт)
        SocketChannel socket = SocketChannel.open();
        socket.connect(address);
        socket.configureBlocking(false); // неблокирующий режим ввода-вывода
        try {
            System.out.println("Welcome to app!");

            while (scanner.hasNextLine()) {
                String command = scanner.nextLine().trim();
                if (!command.isEmpty()){
                    /*
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(command);
                    objectOutputStream.close();
                    ByteBuffer buf = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
                    socket.write(buf);

                     */


                    Organization organization = new Organization();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(organization);
                    objectOutputStream.close();
                    ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

                    // Отправляем данные
                    while (buffer.hasRemaining()) {
                        socket.write(buffer);
                    }

                    // socket.write(buf);
                }

                try {
                    Thread.sleep(1000); // Задержка в 1 секунду
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int bytesRead = socket.read(readBuffer);
                if (bytesRead == -1) {
                    // Сервер закрыл соединение
                    socket.close();
                    return;
                }
                readBuffer.flip();
                byte[] bytes = new byte[readBuffer.remaining()];
                readBuffer.get(bytes);
                String receivedMessage = new String(bytes);
                if (!receivedMessage.isEmpty()) {
                    System.out.println("Server answer: " + receivedMessage);
                }
            }

        } catch (IOException e) {
            System.out.println("Server is not available");
        }

    }
}