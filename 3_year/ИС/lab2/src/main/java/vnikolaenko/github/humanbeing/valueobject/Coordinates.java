package vnikolaenko.github.humanbeing.valueobject;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Coordinates {
    private double x; //Максимальное значение поля: 72
    private int y; //Максимальное значение поля: 78

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return java.util.Objects.equals(x, that.x) &&
                java.util.Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }
}
