package vnikolaenko.github.car.application;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import vnikolaenko.github.car.domain.CarRepository;

@ApplicationScoped
@RequiredArgsConstructor
public class CarExistenceChecker  {
    private final CarRepository carRepository;

    public boolean carExists(Long carId) {
        return carRepository.existsById(carId);
    }
}
