package org.example.managers.commands;

import org.example.managers.DataBaseManager;
import org.example.system.Request;
import org.example.system.Server;

public class RegisterCommand implements BaseCommand {

    @Override
    public String execute(Request request) throws Exception {
        String[] line = request.getMessage().split(" ");
        if (DataBaseManager.insertUser(line[1], line[2])) {
            Server.addUser(request.getLogin());
            return "welcome!";
        } else {
            return "something wrong";
        }
    }

    @Override
    public String getName() {
        return "reg";
    }

    @Override
    public String getDescription() {
        return "registration";
    }
}
