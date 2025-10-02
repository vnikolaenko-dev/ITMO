package vnikolaenko.github.lab1.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import vnikolaenko.github.lab1.db.repository.CarRepository;
import vnikolaenko.github.lab1.db.repository.HumanBeingRepository;
import vnikolaenko.github.lab1.model.obj.Car;
import vnikolaenko.github.lab1.model.obj.HumanBeing;

import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class CarService {
    @Inject
    private CarRepository carRepository;
    @Inject
    private HumanBeingRepository humanBeingRepository;

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public boolean delete(Integer id) {
        List<HumanBeing> humanBeings = humanBeingRepository.findHumanBeingWithCar(id);
        if (!humanBeings.isEmpty()) {
            return false;
        } else {
            carRepository.deleteCarById(id);
            return true;
        }
    }
}
