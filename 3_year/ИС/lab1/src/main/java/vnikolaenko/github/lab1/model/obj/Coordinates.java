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
@Entity(name = "human_being_coordinates")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double x; //Максимальное значение поля: 72

    private int y; //Максимальное значение поля: 78

    @OneToMany(mappedBy = "coordinates")
    private List<HumanBeing> owners = new ArrayList<>();
}
