package vnikolaenko.github.car.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import vnikolaenko.github.car.domain.Car;
import vnikolaenko.github.car.infrastracture.entity.CarEntity;
import vnikolaenko.github.car.infrastracture.mapper.CarMapper;
import vnikolaenko.github.shared.exception.ObjectAlreadyExists;
import vnikolaenko.github.shared.exception.ObjectConnectionException;
import vnikolaenko.github.humanbeing.infrastracture.entity.HumanBeingEntity;
import vnikolaenko.github.car.domain.CarRepository;
import vnikolaenko.github.humanbeing.infrastracture.JpaHumanBeingRepository;


import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class CarService {
    @Inject
    CarRepository carRepository;
    @Inject
    JpaHumanBeingRepository jpaHumanBeingRepository;
    @Inject
    CarMapper carMapper;

    public void save(Car entity) {
        if (carRepository.findByName(entity.getName()) != null) {
            throw new ObjectAlreadyExists("Car already exists");
        }
        carRepository.save(carMapper.toEntity(entity));
    }

    public List<CarEntity> getAll() {
        return carRepository.findAll().list();
    }

    public void deleteById(Long id) {
        List<HumanBeingEntity> humanBeingEntities = jpaHumanBeingRepository.findHumanBeingWithCar(id);
        if (!humanBeingEntities.isEmpty()) {
            throw new ObjectConnectionException("Car connected with human being");
        }
        carRepository.deleteCarById(id);
    }
}
