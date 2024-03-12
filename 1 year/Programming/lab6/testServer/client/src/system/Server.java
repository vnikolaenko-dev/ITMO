package system;

import recources.Organization;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Server {
    private InetSocketAddress address;
    private SocketChannel socket;

    public void initialize(String host, int port) throws IOException {
        this.address = new InetSocketAddress(host, port); // создаем адрес сокета (IP-адрес и порт)
        this.socket = SocketChannel.open();
        socket.connect(address);
        socket.configureBlocking(false); // неблокирующий режим ввода-вывода
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Welcome to app!");
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine().trim();
                ObjectOutputStream objectOutputStream = null;
                if (!command.isEmpty()) {
                    Organization organization = new Organization();
                    Request request = new Request(command, organization);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(request);
                    objectOutputStream.close();
                    ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

                    // Отправляем данные
                    while (buffer.hasRemaining()) {
                        this.socket.write(buffer);
                    }
                }
                objectOutputStream.flush();


                Thread.sleep(2000);

                ByteBuffer buffer = ByteBuffer.allocate(2024);
                this.socket.read(buffer);

                int bytesRead = socket.read(buffer);
                while (bytesRead == -1) {
                    this.socket.read(buffer);
                    bytesRead = socket.read(buffer);
                    // Сервер закрыл соединение
                    // socket.close();
                    // return;
                }

                ByteArrayInputStream bi = new ByteArrayInputStream(buffer.array());
                ObjectInputStream oi = new ObjectInputStream(bi);
                System.out.println(oi.available());

                try {
                    System.out.println("trying");
                    Request request = (Request) oi.readObject();
                    System.out.println("Server answer: \n" + request.getMessage());
                } catch (ClassNotFoundException e) {
                    // Обработка исключения, если класс Request не найден
                    e.printStackTrace();
                } catch (IOException e) {
                    // Обработка исключения ввода-вывода при чтении объекта
                    e.printStackTrace();
                }

                // System.out.println("Server answer: \n" + request.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Server is not available");
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    }
