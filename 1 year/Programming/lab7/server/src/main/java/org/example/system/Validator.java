package org.example.system;

import org.example.*;
import org.example.exceptions.ReplayIdException;
import org.example.exceptions.WrongArgumentException;

import java.time.LocalDate;


/**
 * Данный класс проверяет корректность каждого параметра Organization
 *
 * @author vnikolaenko
 * @see Organization
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
     * @see Coordinates
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
     * @see Coordinates
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
     * @see OrganizationType
     */
    public static void typeIsOk(String arg) throws WrongArgumentException {
        try {
            OrganizationType.valueOf(arg);
        } catch (Exception e) {
            throw new WrongArgumentException("OrganizationType");
        }
    }

    /**
     * Проверяет корректность даты
     *
     * @param arg аргумент строки
     * @throws WrongArgumentException если тип некорректен
     * @see OrganizationType
     */
    public static void dateIsOk(String arg) throws WrongArgumentException {
        try {
            LocalDate.parse(arg);
        } catch (Exception e) {
            throw new WrongArgumentException("Date");
        }
    }

    public static void allDataIsOK(String[] data) throws WrongArgumentException, ReplayIdException {
        try{
            Validator.idIsOk(data[1]);
            Validator.inputIsNotEmpty(data[2], "NAME");
            Validator.coordinateXIsOk(data[3]);
            Validator.coordinateYIsOk(data[4]);
            Validator.inputIsNotEmpty(data[5], "DATE");
            Validator.annualTurnoverIsOk(data[6]);
            Validator.inputIsNotEmpty(data[7], "FullName");
            Validator.employeesCountIsOk(data[8]);
            Validator.typeIsOk(data[9]);
            Validator.inputIsNotEmpty(data[10], "STREET");
            Validator.inputIsNotEmpty(data[11], "ziCode");
        } catch (Exception e){
            throw e;
        }
    }
}
