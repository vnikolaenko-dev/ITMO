package org.example.system;


import org.example.managers.DataBaseManager;
import org.example.managers.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main {
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:1567/studs";
    public static final String DATABASE_URL_HELIOS = "jdbc:postgresql://pg:5432/studs";
    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.exit(0);
        }

        Properties info = new Properties();
        info.load(new FileInputStream(args[1]));
        DataBaseManager.setURL(DATABASE_URL_HELIOS);
        DataBaseManager.setUsername(info.getProperty("user"));
        DataBaseManager.setPassword(info.getProperty("password"));
        DataBaseManager.connectToDataBase();

        int port = Integer.parseInt(args[0]);
        try {
            Server server = new Server();
            server.initialize(port);
            server.start();
        } catch (Exception e){
            LOGGER.warn("Something wrong with settings of server or database" + e.getMessage());
            System.exit(0);
        }

    }
}