package data.generators;


import data.Address;
import data.Coordinates;
import data.Organization;
import data.OrganizationType;
import exceptions.BuildObjectException;
import exceptions.ReplayIdException;
import exceptions.WrongArgumentException;
import managers.InputValidator;
import system.TextColor;

import java.util.Scanner;

public class OrganizationGenerator {
    public static Object createOrganization(Long id) throws WrongArgumentException, BuildObjectException {
        System.out.println("Generate...");

        String input, a, b;
        Coordinates coordinates;
        Address postalAddress;

        Scanner scanner = new Scanner(System.in);

        Organization organization;
        if (id == 0){
            organization = new Organization();
        }
        else{
            organization = new Organization(id);
        }


        while (true) {
            try {
                System.out.print("Input name (" + TextColor.ANSI_PURPLE + "String" + TextColor.ANSI_RESET + "): ");
                input = scanner.nextLine();
                InputValidator.inputIsNotEmpty(input, "NAME");
                organization.setName(input);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input x (" + TextColor.ANSI_PURPLE + "Integer" + TextColor.ANSI_RESET + "): ");
                input = scanner.nextLine();
                InputValidator.coordinatesIsOk(input);
                a = input;
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input y (" + TextColor.ANSI_PURPLE + "Integer" + TextColor.ANSI_RESET + "): ");
                input = scanner.nextLine();
                InputValidator.coordinatesIsOk(input);
                b = input;
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        coordinates = new Coordinates(Integer.parseInt(a), Integer.parseInt(b));
        organization.setCoordinates(coordinates);

        while (true) {
            try {
                System.out.print("Input annualTurnover (" + TextColor.ANSI_PURPLE + "Double" + TextColor.ANSI_RESET + "): ");
                input = scanner.nextLine();
                InputValidator.annualTurnoverIsOk(input);
                organization.setAnnualTurnover(Double.parseDouble(input));
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input fullName (" + TextColor.ANSI_PURPLE + "String" + TextColor.ANSI_RESET + "): ");
                input = scanner.nextLine();
                InputValidator.inputIsNotEmpty(input, "fullName");
                organization.setFullName(input);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input employeesCount (" + TextColor.ANSI_PURPLE + "Integer" + TextColor.ANSI_RESET + "): ");
                input = scanner.nextLine();
                InputValidator.employeesCountIsOk(input);
                organization.setEmployeesCount(Integer.parseInt(input));
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input OrganizationType (" + TextColor.ANSI_PURPLE + "PUBLIC, TRUST, OPEN_JOINT_STOCK_COMPANY" + TextColor.ANSI_RESET + "): ");
                input = scanner.nextLine();
                InputValidator.typeIsOk(input);
                organization.setType(OrganizationType.valueOf(input));
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input street (" + TextColor.ANSI_PURPLE + "String" + TextColor.ANSI_RESET + "): ");
                a = scanner.nextLine();
                InputValidator.inputIsNotEmpty(a, "street");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Input zipCode (" + TextColor.ANSI_PURPLE + "String" + TextColor.ANSI_RESET + "): ");
                b = scanner.nextLine();
                InputValidator.inputIsNotEmpty(b, "zipCode");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        postalAddress = new Address(a, b);
        organization.setPostalAddress(postalAddress);
        return organization;
    }
}
