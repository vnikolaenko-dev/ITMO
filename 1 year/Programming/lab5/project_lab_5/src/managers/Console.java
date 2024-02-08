package managers;

import java.io.InputStream;
import java.util.Date;
import java.util.Scanner;

public class Console {
    private Date date;

    public void start(InputStream input) {
        Scanner scanner = new Scanner(input);
        CommandManager commandManager = new CommandManager();
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            try {
                commandManager.startExecuting(command);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Date getDate() {
        return date;
    }
}
