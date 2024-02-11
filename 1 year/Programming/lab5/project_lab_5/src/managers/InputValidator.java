package managers;

import data.OrganizationType;
import data.generators.IdGenerator;
import exceptions.ReplayIdException;
import exceptions.WrongArgumentException;

public class InputValidator {
    public static void idIsOk(String arg) throws WrongArgumentException, ReplayIdException {
        long id;
        try {
            id = Long.parseLong(arg);
        } catch (Exception e) {
            throw new WrongArgumentException("ID");
        }

        if (!IdGenerator.idIsUnic(id)){
            throw new ReplayIdException(id);
        }
    }
    public static void inputIsNotEmpty(String arg, String data) throws WrongArgumentException {
        if (arg.isEmpty()) {
            throw new WrongArgumentException(data);
        }
    }

    public static void coordinatesIsOk(String arg) throws WrongArgumentException {
        try {
            Integer.parseInt(arg);
        } catch (Exception e) {
            throw new WrongArgumentException("COORDINATE");
        }
    }

    public static void annualTurnoverIsOk(String arg) throws WrongArgumentException {
        try {
            Double.parseDouble(arg);
        } catch (Exception e) {
            throw new WrongArgumentException("annualTurnover");
        }
    }

    public static void employeesCountIsOk(String arg) throws WrongArgumentException {
        try {
            Integer.parseInt(arg);
        } catch (Exception e) {
            throw new WrongArgumentException("employeesCount");
        }
    }

    public static void typeIsOk(String arg) throws WrongArgumentException {
        try {
            OrganizationType.valueOf(arg);
        } catch (Exception e) {
            throw new WrongArgumentException("OrganizationType");
        }
    }
}
