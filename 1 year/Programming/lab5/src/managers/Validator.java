package managers;

import data.OrganizationType;
import data.generators.IdGenerator;
import exceptions.ReplayIdException;
import exceptions.WrongArgumentException;


/**
 * Данный класс проверяет корректность каждого параметра Organization
 *
 * @author vnikolaenko
 * @see data.Organization
 * @since 1.0
 */
public class Validator {
    /**
     * Проверяет корректность ID
     *
     * @param arg аргумент строки
     * @throws WrongArgumentException если возникла проблема
     * @throws ReplayIdException      если такой ID уже зарезервирован
     */
    public static void idIsOk(String arg) throws WrongArgumentException, ReplayIdException {
        long id;
        try {
            id = Long.parseLong(arg);
        } catch (Exception e) {
            throw new WrongArgumentException("ID");
        }

        if (!IdGenerator.idIsUnique(id)) {
            throw new ReplayIdException(id);
        }
    }

    /**
     * Проверяет, что значение строки не null
     *
     * @param arg  аргумент строки
     * @param data какой тип данных из Organization мы проверяем
     * @throws WrongArgumentException если значение arg null
     */
    public static void inputIsNotEmpty(String arg, String data) throws WrongArgumentException {
        if (arg.isEmpty() || arg.trim().isEmpty()) {
            throw new WrongArgumentException(data);
        }
    }

    /**
     * Проверяет корректность координат по X
     *
     * @param arg аргумент строки
     * @throws WrongArgumentException если координата некорректна
     * @see data.Coordinates
     */
    public static void coordinateXIsOk(String arg) throws WrongArgumentException {
        try {
            int n = Integer.parseInt(arg);
            if (n <= -763){
                throw new WrongArgumentException("X");
            }
        } catch (Exception e) {
            throw new WrongArgumentException("X");
        }
    }

    /**
     * Проверяет корректность координат по Y
     *
     * @param arg аргумент строки
     * @throws WrongArgumentException если координата некорректна
     * @see data.Coordinates
     */
    public static void coordinateYIsOk(String arg) throws WrongArgumentException {
        try {
            int n = Integer.parseInt(arg);
            if (n >= 579){
                throw new WrongArgumentException("Y");
            }
        } catch (Exception e) {
            throw new WrongArgumentException("Y");
        }
    }

    /**
     * Проверяет корректность годового оборота
     *
     * @param arg аргумент строки
     * @throws WrongArgumentException если годовой оборот некорректен
     */
    public static void annualTurnoverIsOk(String arg) throws WrongArgumentException {
        try {
            double n = Double.parseDouble(arg);
            if (n <= 0){
                throw new WrongArgumentException("annualTurnover");
            }
        } catch (Exception e) {
            throw new WrongArgumentException("annualTurnover");
        }
    }

    /**
     * Проверяет корректность числа работников
     *
     * @param arg аргумент строки
     * @throws WrongArgumentException если число работников некорректно
     */
    public static void employeesCountIsOk(String arg) throws WrongArgumentException {
        try {
            int n = Integer.parseInt(arg);
            if (n <= 0){
                throw new WrongArgumentException("employeesCount");
            }
        } catch (Exception e) {
            throw new WrongArgumentException("employeesCount");
        }
    }

    /**
     * Проверяет корректность типа
     *
     * @param arg аргумент строки
     * @throws WrongArgumentException если тип некорректен
     * @see data.OrganizationType
     */
    public static void typeIsOk(String arg) throws WrongArgumentException {
        try {
            OrganizationType.valueOf(arg);
        } catch (Exception e) {
            throw new WrongArgumentException("OrganizationType");
        }
    }
}
