package managers.generators;


import exceptions.BuildOrganizationException;
import exceptions.WrongArgumentException;
import recources.*;
import system.TextColor;

import java.util.Scanner;

/**
 * Класс генерирует объект Organization, поэтапно запрашивая данные
 *
 * @author vnikolaenko
 * @see Organization
 * @since 1.0
 */
public class OrganizationGenerator {
    /**
     * Метод поэтапно запрашивает данные и проверяет их для создания объекта (Organization)
     *
     * @author vnikolaenko
     * @see Organization
     * @since 1.0
     */
    public static Organization createOrganization(Long id) throws WrongArgumentException, BuildOrganizationException {
        System.out.println(TextColor.ANSI_BLUE + "Welcome to Organisation Builder." + TextColor.ANSI_RESET);

        String input, a, b;
        Coordinates coordinates;
        Address postalAddress;

        Scanner scanner = new Scanner(System.in);
        System.out.println(id);

        Organization organization;
        if (id == 0) {
            organization = new Organization();
        } else {
            organization = new Organization(id);
        }

        while (true) {
            try {
                System.out.print("Input name (" + TextColor.ANSI_PURPLE + "String" + TextColor.ANSI_RESET + "): ");
                input = scanner.nextLine();
                Validator.inputIsNotEmpty(input, "NAME");
                organization.setName(input);
                break;

            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input x (" + TextColor.ANSI_PURPLE + "Integer" + TextColor.ANSI_RESET + ") > -763: ");
                input = scanner.nextLine();
                Validator.coordinateXIsOk(input);
                a = input;
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input y (" + TextColor.ANSI_PURPLE + "Integer" + TextColor.ANSI_RESET + ") < 580: ");
                input = scanner.nextLine();
                Validator.coordinateYIsOk(input);
                b = input;
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        coordinates = new Coordinates(Integer.parseInt(a), Integer.parseInt(b));
        organization.setCoordinates(coordinates);

        while (true) {
            try {
                System.out.print("Input annualTurnover (" + TextColor.ANSI_PURPLE + "Double" + TextColor.ANSI_RESET + ") > 0: ");
                input = scanner.nextLine();
                Validator.annualTurnoverIsOk(input);
                organization.setAnnualTurnover(Double.parseDouble(input));
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input fullName (" + TextColor.ANSI_PURPLE + "String" + TextColor.ANSI_RESET + "): ");
                input = scanner.nextLine();
                Validator.inputIsNotEmpty(input, "fullName");
                organization.setFullName(input);
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input employeesCount (" + TextColor.ANSI_PURPLE + "Integer" + TextColor.ANSI_RESET + ") > 0: ");
                input = scanner.nextLine();
                Validator.employeesCountIsOk(input);
                organization.setEmployeesCount(Integer.parseInt(input));
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input OrganizationType (" + TextColor.ANSI_PURPLE + "PUBLIC, TRUST, OPEN_JOINT_STOCK_COMPANY" + TextColor.ANSI_RESET + "): ");
                input = scanner.nextLine();
                Validator.typeIsOk(input);
                organization.setType(OrganizationType.valueOf(input));
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input street (" + TextColor.ANSI_PURPLE + "String" + TextColor.ANSI_RESET + "): ");
                a = scanner.nextLine();
                Validator.inputIsNotEmpty(a, "street");
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input zipCode (" + TextColor.ANSI_PURPLE + "String" + TextColor.ANSI_RESET + "): ");
                b = scanner.nextLine();
                Validator.inputIsNotEmpty(b, "zipCode");
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        postalAddress = new Address(a, b);
        organization.setPostalAddress(postalAddress);
        System.out.println("Generating ...");
        return organization;
    }
}
