package org.example.system;


import org.example.managers.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Perform additional actions before ending the program...");
            try {
                CommandManager.startExecutingServerMode(new Request("save", null, null));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));


        if (args.length != 2) {

        }
        int port = Integer.parseInt(args[0]);
        String host = args[1];

        try {
            Server server = new Server();
            server.initialize(port, host);
            server.start();
        } catch (Exception e){
            LOGGER.warn("Something wrong with settings of server or file" + e.getMessage());
            System.exit(0);
        }

    }
}