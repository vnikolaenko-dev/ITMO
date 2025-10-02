package vnikolaenko.github.lab1.model.obj;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "human_being_car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name; //Поле не может быть null

    private boolean cool;

    @OneToMany(mappedBy = "car")
    private List<HumanBeing> owners = new ArrayList<>();
}
