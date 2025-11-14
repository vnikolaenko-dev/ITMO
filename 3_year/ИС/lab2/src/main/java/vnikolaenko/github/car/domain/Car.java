package vnikolaenko.github.car.domain;

import jakarta.transaction.Transactional;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {
    private String name;
    private boolean cool;
}
