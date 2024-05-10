package org.example.managers.commands;

import org.example.managers.DataBaseManager;
import org.example.system.Request;
import org.example.system.Server;

public class LoginCommand implements BaseCommand {

    @Override
    public String execute(Request request) throws Exception {
        if (DataBaseManager.checkUser(request.getLogin(), request.getPassword())) {
            Server.addUser(request.getLogin());
            return "welcome!";
        } else {
            return "uncorrected username or password";
        }
    }

    @Override
    public String getName() {
        return "log";
    }

    @Override
    public String getDescription() {
        return "login";
    }
}
