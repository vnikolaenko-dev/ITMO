package vnikolaenko.github.lab1.network.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import vnikolaenko.github.lab1.model.obj.Mood;
import vnikolaenko.github.lab1.model.obj.WeaponType;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HumanBeingDTO {
    private Integer id;
    private String name;
    private CoordinatesDTO coordinates;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime creationDate;
    private boolean realHero;
    private Boolean hasToothpick;
    private CarDTO car;
    private Mood mood;
    private float impactSpeed;
    private Integer minutesOfWaiting;
    private WeaponType weaponType;
}