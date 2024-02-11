package data;

public class Coordinates {
    private Integer x; //Значение поля должно быть больше -763, Поле не может быть null
    private Integer y; //Максимальное значение поля: 579, Поле не может быть null

    public Coordinates(Integer x, Integer y){
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
