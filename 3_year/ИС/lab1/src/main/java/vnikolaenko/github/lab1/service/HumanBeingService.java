package vnikolaenko.github.lab1.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import vnikolaenko.github.lab1.db.repository.CarRepository;
import vnikolaenko.github.lab1.db.repository.CoordinatesRepository;
import vnikolaenko.github.lab1.db.repository.HumanBeingRepository;
import vnikolaenko.github.lab1.model.obj.HumanBeing;
import vnikolaenko.github.lab1.model.obj.WeaponType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
@NoArgsConstructor
public class HumanBeingService {
    @Inject
    private HumanBeingRepository humanBeingRepository;
    @Inject
    private CarRepository carRepository;
    @Inject
    private CoordinatesRepository coordinatesRepository;

    private void validateHumanBeing(HumanBeing humanBeing) {
        if (humanBeing.getName() == null || humanBeing.getName().isEmpty()) {
            throw new IllegalArgumentException("HumanBeing name cannot be empty");
        } else if (humanBeing.getCoordinates() == null) {
            throw new IllegalArgumentException("HumanBeing coordinates cannot be empty");
        } else if (humanBeing.getHasToothpick() == null) {
            throw new IllegalArgumentException("HumanBeing hasToothpick cannot be empty");
        } else if (humanBeing.getCar() == null) {
            throw new IllegalArgumentException("HumanBeing car cannot be empty");
        } else if (humanBeing.getMood() == null) {
            throw new IllegalArgumentException("HumanBeing mood cannot be empty");
        } else if (humanBeing.getImpactSpeed() < -309) {
            throw new IllegalArgumentException("HumanBeing impact speed cannot be less than -309");
        } else if (humanBeing.getMinutesOfWaiting() == null) {
            throw new IllegalArgumentException("HumanBeing minutes cannot be empty");
        } else if (humanBeing.getWeaponType() == null) {
            throw new IllegalArgumentException("HumanBeing weapon type cannot be empty");
        }

        if (humanBeing.getCoordinates().getId() != null) {
            humanBeing.setCoordinates(coordinatesRepository.findById(humanBeing.getCoordinates().getId()));
        }

        if (humanBeing.getCoordinates().getX() > 72 || humanBeing.getCoordinates().getY() > 78) {
            throw new IllegalArgumentException("HumanBeing coordinates X cannot be greater than 72 and Y 78");
        }

        if (humanBeing.getCar().getId() != null) {
            humanBeing.setCar(carRepository.findById(humanBeing.getCar().getId()));
        }

        if (humanBeing.getCar().getName() == null) {
            throw new IllegalArgumentException("HumanBeing car name cannot be empty");
        }
    }

    public boolean save(HumanBeing humanBeing) {
        validateHumanBeing(humanBeing);

        if (humanBeing.getCar().getId() != null) {
            humanBeing.setCar(carRepository.findById(humanBeing.getCar().getId()));
        } else {
            carRepository.save(humanBeing.getCar());
        }

        if (humanBeing.getCoordinates().getId() != null) {
            humanBeing.setCoordinates(coordinatesRepository.findById(humanBeing.getCoordinates().getId()));
        } else {
            coordinatesRepository.save(humanBeing.getCoordinates());
        }

        humanBeingRepository.save(humanBeing);
        return true;
    }

    public boolean delete(Integer id) {
        humanBeingRepository.deleteHumanBeingById(id);
        return true;
    }

    public List<HumanBeing> getByName(int page, int size, String name) {
        return humanBeingRepository.findByName(page, size, name);
    }


    public boolean deleteWithImpactSpeed(Integer id) {
        return humanBeingRepository.deleteWithImpactSpeed(id);
    }

    public Integer deleteHumanBeingWithoutScraper(){
        return humanBeingRepository.deleteHumanBeingWithoutScraper();
    }

    public Double avgImpactSpeed(){
        return humanBeingRepository.avgImpactSpeed();
    }

    public List<HumanBeing> findByWeaponTypeLessThan(WeaponType type) {
        return humanBeingRepository.findByWeaponTypeLessThan(type);
    }

    public void assignRedLadaToHumansWithoutCar(){
        humanBeingRepository.assignRedLadaToHumansWithoutCar();
    }


    public List<HumanBeing> getAllWithOrder(int page, int size, String arg, String order, String name) {
        if (!Objects.equals(name, "null")) {
            return getByName(page, size, name);
        }
        return humanBeingRepository.findAllWithOrder(page, size, arg, order);
    }
}
