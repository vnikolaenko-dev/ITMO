package system;

import recources.Organization;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 5760575944040770153L;
    private String message = null;
    private Organization organization = null;

    public Request(String message, Organization organization) {
        this.message = message;
        this.organization = organization;
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
}
