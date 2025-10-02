package vnikolaenko.github.lab1.model.obj;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "human_being")
public class HumanBeing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Column(nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Coordinates coordinates; //Поле не может быть null

    @Column(nullable = false)
    private ZonedDateTime creationDate = ZonedDateTime.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private boolean realHero;

    @Column(nullable = false)
    private Boolean hasToothpick; //Поле не может быть null

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Car car; //Поле не может быть null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Mood mood; //Поле не может быть null

    @Column(name = "impactspeed", nullable = false)
    // @Min(value = -308)
    private float impactSpeed; //Значение поля должно быть больше -309

    @Column(nullable = false)
    private Integer minutesOfWaiting; //Поле не может быть null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WeaponType weaponType; //Поле не может быть null
}
