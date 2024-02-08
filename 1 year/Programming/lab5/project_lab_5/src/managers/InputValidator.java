package managers;

import data.Organization;
import data.OrganizationType;
import exceptions.UnknownCommandException;
import exceptions.WrongArgumentException;

import java.util.SplittableRandom;

public class InputValidator {

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
