package vnikolaenko.github.car.domain;

import jakarta.enterprise.context.ApplicationScoped;
import vnikolaenko.github.car.infrastracture.JpaCarRepository;


@ApplicationScoped
public class CarRepository implements JpaCarRepository {
}
