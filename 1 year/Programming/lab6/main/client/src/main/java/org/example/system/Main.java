package org.example.system;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Wrong argument");
            return;
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        Server server = new Server();
        server.initialize(host, port);
        server.start();
    }
}