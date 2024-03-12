package managers.commands;

import recources.Organization;
import exceptions.RecursionException;
import exceptions.RootException;
import managers.CollectionManager;
import managers.CommandManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

/**
 * Данная команда исполняет скрипт из файла
 *
 * @author vnikolaenko
 * @see BaseCommand
 * @since 1.0
 */
public class ExecuteScriptCommand implements BaseCommand {
    private static Stack<File> st = new Stack<>();

    @Override
    public String execute(String[] args) throws Exception {
        File file = new File(args[1]);
        if (!file.canRead()) {
            throw new RootException("You do not have enough rights to read the file");
        }
        if (st.isEmpty()) {
            st.add(file);
        }

        st.add(file);
        String path = args[1];
        var br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        String line;
        String[] org = new String[10];
        while ((line = br.readLine()) != null) {
            if (line.split(" ")[0].equals("insert")) {
                String key = line.split(" ")[0];
                for (int n = 0; n < 10; n++) {
                    if ((line = br.readLine()) != null) {
                        org[n] = line;
                    }
                }
                CollectionManager.add(key, new Organization(org));
            } else {
                if (line.contains("execute_script")) {
                    File file_new = new File(line.split(" ")[1]);
                    if (!file_new.canRead()) {
                        throw new RootException("You do not have enough rights to read the file");
                    }
                    if (st.contains(file_new)) {
                        System.out.println("Recursion to file " + file.getName() + " was skipped");
                    } else {
                        CommandManager.startExecuting(line);
                    }
                } else {
                    CommandManager.startExecuting(line);
                }
            }
        }
        st.pop();
        return "script was completed";
    }

    @Override
    public String getName() {
        return "execute_script file_name";
    }

    @Override
    public String getDescription() {
        return "execute script from file";
    }
}
