
package managers;

import exceptions.RootException;
import recources.Organization;
import system.Request;
import system.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Stack;

/**
 * Данная команда исполняет скрипт из файла
 *
 * @author vnikolaenko
 * @since 1.0
 */
public class ExecuteScript {
    private static Stack<File> st = new Stack<>();


    public static void execute(String command) throws Exception {
        File file = new File(command.split( " ")[1]);
        if (!file.canRead()) {
            throw new RootException("You do not have enough rights to read the file");
        }
        if (st.isEmpty()) {
            st.add(file);
        }

        st.add(file);
        String path = command.split( " ")[1];
        var br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        String line;
        String[] org = new String[12];
        while ((line = br.readLine()) != null) {
            if (line.split(" ")[0].equals("insert")) {
                String key = line.split(" ")[1];
                for (int n = 0; n < 11; n++) {
                    if (n==0){
                        continue;
                    }
                    else if (n == 4){
                        org[n] = LocalDate.now().toString();
                    } else if ((line = br.readLine()) != null) {
                        org[n] = line;
                    }
                }
                Server.sendRequest(new Request("insert " + key, new Organization(org), key));
            } else {
                if (line.contains("execute_script")) {
                    File file_new = new File(line.split(" ")[1]);
                    if (!file_new.canRead()) {
                        throw new RootException("You do not have enough rights to read the file");
                    }
                    if (st.contains(file_new)) {
                        System.out.println("Recursion to file " + file.getName() + " was skipped");
                    } else {
                        Server.sendRequest(new Request(line, new Organization(org), null));
                    }
                } else {
                    Server.sendRequest(new Request(line, new Organization(org), null));
                }
            }
        }
        st.pop();
        System.out.println("script was completed");
    }

    public String getName() {
        return "execute_script file_name";
    }

    public String getDescription() {
        return "execute script from file";
    }
}
