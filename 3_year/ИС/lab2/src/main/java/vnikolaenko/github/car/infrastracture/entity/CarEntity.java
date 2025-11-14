package vnikolaenko.github.car.infrastracture.entity;

import jakarta.persistence.*;
import lombok.*;
import vnikolaenko.github.humanbeing.infrastracture.entity.HumanBeingEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "human_being_car")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; //Поле не может быть null

    private boolean cool;

    @OneToMany(mappedBy = "car")
    private List<HumanBeingEntity> owners = new ArrayList<>();

}
