package vnikolaenko.github.humanbeing.domen;

import io.quarkus.arc.All;
import lombok.*;
import vnikolaenko.github.car.domain.Car;
import vnikolaenko.github.car.infrastracture.entity.CarEntity;
import vnikolaenko.github.humanbeing.valueobject.Coordinates;
import vnikolaenko.github.humanbeing.valueobject.Mood;
import vnikolaenko.github.humanbeing.valueobject.WeaponType;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HumanBeing {
    private String name; //Поле не может быть null, Строка не может быть пустой

    private Coordinates coordinates; //Поле не может быть null

    private ZonedDateTime creationDate = ZonedDateTime.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private boolean realHero;

    private Boolean hasToothpick = false; //Поле не может быть null

    private Car car; //Поле не может быть null

    private Mood mood; //Поле не может быть null

    private float impactSpeed; //Значение поля должно быть больше -309

    private Integer minutesOfWaiting; //Поле не может быть null

    private WeaponType weaponType; //Поле не может быть null
}
