package org.example.system;

import org.example.*;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 5760575944040770153L;
    private String message;
    private Organization organization;
    private String key = null;
    private String login = null;
    private String password = null;

    public Request(String message, Organization organization, String key) {
        this.message = message;
        this.organization = organization;
        this.key = key;
    }

    public Request(String message, Organization organization, String key, String login, String password) {
        this.message = message;
        this.organization = organization;
        this.key = key;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
