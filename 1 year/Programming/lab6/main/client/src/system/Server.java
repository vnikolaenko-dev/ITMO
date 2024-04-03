package system;

import exceptions.BuildOrganizationException;
import exceptions.WrongArgumentException;
import managers.ExecuteScript;
import managers.generators.OrganizationGenerator;
import recources.Organization;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private static SocketChannel socket;

    public void initialize(String host, int port) throws IOException {
        try {
            InetSocketAddress address = new InetSocketAddress(host, port); // создаем адрес сокета (IP-адрес и порт)
            socket = SocketChannel.open();
            socket.connect(address);
            socket.configureBlocking(false); // неблокирующий режим ввода-вывода
        } catch (RuntimeException | ConnectException e) {
            System.out.println("Server " + host + " on port " + port + " is not available");
            System.exit(1);
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Welcome to app!");
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine().trim();
                if (command.equals("exit")) {
                    System.exit(1);
                }
                if (!command.isEmpty()) {
                    Organization organization = new Organization();
                    String key = null;
                    if (command.contains("insert") || command.contains("update") || command.contains("replace_if_greater")) {
                        if (command.split(" ").length == 2) {
                            key = command.split(" ")[1];
                        }
                        organization = OrganizationGenerator.createOrganization(0L);
                        System.out.println(organization.getId());
                    } else if (command.contains("execute_script")){
                        ExecuteScript.execute(command);
                    }
                    Request request = new Request(command, organization, key);
                    sendRequest(request);
                }
            }

        } catch (IOException e) {
            System.out.println("Server is not available");
            System.out.println(e.getMessage() + " " + e);
        } catch (WrongArgumentException | BuildOrganizationException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void sendRequest(Request request) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        objectOutputStream.close();
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

        // Отправляем данные
        while (buffer.hasRemaining()) {
            socket.write(buffer);
        }

        try {
            Request request_server = getAnswer();
            System.out.println(TextColor.ANSI_BLUE + "Server answer: \n" + TextColor.ANSI_RESET + request_server.getMessage());
        } catch (ClassNotFoundException e) {
            // Обработка исключения, если класс Request не найден
            System.out.println(TextColor.ANSI_RED + "Wrong answer from server" + TextColor.ANSI_RESET);
        } catch (IOException e) {
            // Обработка исключения ввода-вывода при чтении объекта
            System.out.println(TextColor.ANSI_RED + "Something wrong while reading answer from server" + TextColor.ANSI_RESET);
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Request getAnswer() throws IOException, InterruptedException, ClassNotFoundException {
        Thread.sleep(2000);
        ArrayList<ByteBuffer> bufferList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            socket.read(buffer);
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

        ByteArrayInputStream bi = new ByteArrayInputStream(bigBuffer.array());
        ObjectInputStream oi = new ObjectInputStream(bi);

        return (Request) oi.readObject();
    }
}
