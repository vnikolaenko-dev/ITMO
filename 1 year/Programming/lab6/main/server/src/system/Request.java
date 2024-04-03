package system;

import recources.Organization;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 5760575944040770153L;
    private String message;
    private Organization organization;
    private String key = null;

    public Request(String message, Organization organization, String key) {
        this.message = message;
        this.organization = organization;
        this.key = key;
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
