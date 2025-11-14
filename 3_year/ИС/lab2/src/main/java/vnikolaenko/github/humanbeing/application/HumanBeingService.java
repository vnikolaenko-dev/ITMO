package vnikolaenko.github.humanbeing.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import vnikolaenko.github.car.infrastracture.entity.CarEntity;
import vnikolaenko.github.car.infrastracture.mapper.CarMapper;
import vnikolaenko.github.humanbeing.domen.HumanBeing;
import vnikolaenko.github.humanbeing.infrastracture.mapper.HumanBeingMapper;
import vnikolaenko.github.humanbeing.infrastracture.entity.HumanBeingEntity;
import vnikolaenko.github.humanbeing.valueobject.WeaponType;
import vnikolaenko.github.car.domain.CarRepository;
import vnikolaenko.github.humanbeing.infrastracture.JpaHumanBeingRepository;


import java.util.List;
import java.util.Objects;

@ApplicationScoped
@NoArgsConstructor
public class HumanBeingService {
    @Inject
    JpaHumanBeingRepository jpaHumanBeingRepository;
    @Inject
    CarRepository carRepository;
    @Inject
    HumanBeingValidator humanBeingValidator;
    @Inject
    HumanBeingMapper humanBeingMapper;
    @Inject
    CarMapper carMapper;

    public void save(HumanBeing humanBeing, Long id) {
        System.out.println(humanBeing);
        humanBeingValidator.validateHumanBeing(humanBeing);

        System.out.println(humanBeing);

        // Обрабатываем машину
        CarEntity carEntity;
        if (id == null) {
            // Новая машина - сохраняем
            carEntity = carMapper.toEntity(humanBeing.getCar());
            System.out.println(carEntity);
            carRepository.save(carEntity);
            id = carEntity.getId();
        } else {
            // Существующая машина - получаем из БД
            carEntity = carRepository.findById(id);
            if (carEntity == null) {
                throw new IllegalArgumentException("Car with id " + id + " not found");
            }
        }

        // Создаем HumanBeingEntity и устанавливаем правильную CarEntity
        HumanBeingEntity humanBeingEntity = humanBeingMapper.toEntity(humanBeing);
        System.out.println(humanBeingEntity);
        humanBeingEntity.setCar(carEntity); // Используем уже сохраненную/найденную сущность

        jpaHumanBeingRepository.save(humanBeingEntity);
    }

    public void update(HumanBeing humanBeing, Long id) {
        humanBeingValidator.validateHumanBeing(humanBeing);

        if (id != null) {
            carRepository.save(carMapper.toEntity(humanBeing.getCar()));
        }

        HumanBeingEntity humanBeingEntity = humanBeingMapper.toEntity(humanBeing);
        humanBeingEntity.setCar(carRepository.findById(humanBeingEntity.getCar().getId()));

        jpaHumanBeingRepository.save(humanBeingEntity);
    }

    public void deleteById(Long id) {
        jpaHumanBeingRepository.deleteHumanBeingById(id);
    }

    public List<HumanBeingEntity> getByName(int page, int size, String name) {
        return jpaHumanBeingRepository.findByName(page, size, name);
    }

    public void deleteWithImpactSpeed(Integer id) {
        jpaHumanBeingRepository.deleteWithImpactSpeed(id);
    }

    public Long deleteHumanBeingWithoutScraper() {
        return jpaHumanBeingRepository.deleteHumanBeingWithoutScraper();
    }

    public Double avgImpactSpeed() {
        return jpaHumanBeingRepository.avgImpactSpeed();
    }

    public List<HumanBeingEntity> findByWeaponTypeLessThan(WeaponType type) {
        return jpaHumanBeingRepository.findByWeaponTypeLessThan(type);
    }

    public void assignRedLadaToHumansWithoutCar() {
        jpaHumanBeingRepository.assignRedLadaToHumansWithoutCar();
    }


    public List<HumanBeingEntity> getAllWithOrder(int page, int size, String arg, String order, String name) {
        if (!Objects.equals(name, "null")) {
            return getByName(page, size, name);
        }
        return jpaHumanBeingRepository.findAllWithOrder(page, size, arg, order);
    }
}
