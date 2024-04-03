package recources;

import java.io.Serializable;

/**
 * Модель объекта "Координаты"
 * Содержит геттеры/сеттеры каждого поля класса
 * Некоторые поля имеют ограничения. Он подписан под каждым методом поля
 *
 * @author vnikolaenko
 * @since 1.0
 */
public class Coordinates implements Serializable {
    /**
     * Координата по X
     * Значение поля должно быть больше -763, Поле не может быть null
     *
     * @since 1.0
     */
    private Integer x;
    /**
     * Координата по Y
     * Максимальное значение поля: 579, Поле не может быть null
     *
     * @since 1.0
     */
    private Integer y;

    /**
     * Базовый конструктор
     *
     * @since 1.0
     */
    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
