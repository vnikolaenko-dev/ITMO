package org.example.recources;

import java.io.Serializable;

/**
 * Модель объекта "Адрес"
 * Содержит геттеры/сеттеры каждого поля класса
 * Некоторые поля имеют ограничения. Он подписан под каждым методом поля
 *
 * @author vnikolaenko
 * @since 1.0
 */
public class Address implements Serializable {
    /**
     * Название улицы
     * Поле не может быть null
     *
     * @since 1.0
     */
    private String street;
    /**
     * Почтовый код
     * Поле не может быть null
     *
     * @since 1.0
     */
    private String zipCode;

    /**
     * Базовый конструктор
     *
     * @since 1.0
     */
    public Address(String street, String zipCode) {
        this.street = street;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
