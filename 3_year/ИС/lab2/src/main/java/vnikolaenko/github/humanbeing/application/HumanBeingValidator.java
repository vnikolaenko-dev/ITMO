package vnikolaenko.github.humanbeing.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import vnikolaenko.github.humanbeing.domen.HumanBeing;
import vnikolaenko.github.humanbeing.infrastracture.entity.HumanBeingEntity;
import vnikolaenko.github.car.domain.CarRepository;


@ApplicationScoped
@NoArgsConstructor
public class HumanBeingValidator {
    public void validateHumanBeing(HumanBeing humanBeing) {
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

        if (humanBeing.getCoordinates().getX() > 72 || humanBeing.getCoordinates().getY() > 78) {
            throw new IllegalArgumentException("HumanBeing coordinates X cannot be greater than 72 and Y 78");
        }

        if (humanBeing.getCar().getName() == null) {
            throw new IllegalArgumentException("HumanBeing car name cannot be empty");
        }
    }
}
