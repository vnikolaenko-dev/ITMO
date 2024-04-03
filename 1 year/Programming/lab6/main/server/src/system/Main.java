package system;


import managers.CommandManager;


public class Main {
    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Perform additional actions before ending the program...");
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
            System.out.println("Something wrong with settings of server or file");
            System.exit(0);
        }

    }
}