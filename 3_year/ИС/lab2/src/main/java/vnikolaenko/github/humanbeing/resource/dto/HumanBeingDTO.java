package vnikolaenko.github.humanbeing.resource.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vnikolaenko.github.car.resource.dto.CarDto;
import vnikolaenko.github.humanbeing.valueobject.Coordinates;
import vnikolaenko.github.humanbeing.valueobject.Mood;
import vnikolaenko.github.humanbeing.valueobject.WeaponType;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HumanBeingDTO {
    private Long id;
    private String name;
    private Coordinates coordinates;
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime creationDate;
    private boolean realHero;
    private Boolean hasToothpick;
    private CarDto car;
    private Mood mood;
    private float impactSpeed;
    private Integer minutesOfWaiting;
    private WeaponType weaponType;
}