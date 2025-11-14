package vnikolaenko.github.car.infrastracture;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import vnikolaenko.github.car.infrastracture.entity.CarEntity;


public interface JpaCarRepository extends PanacheRepository<CarEntity> {

    default CarEntity findByName(String name) {
        return find("name", name).firstResult();
    }

    default CarEntity findByIdSafe(Long id) {
        return findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
    }

    @Transactional
    default void deleteCarById(Long id) {
        boolean deleted = deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Failed to delete car with id: " + id);
        }
    }

    @Transactional
    default Long save(CarEntity carEntity) {
        if (carEntity.getId() == null) {
            persist(carEntity);
            // После persist() ID будет установлен
            return carEntity.getId();
        } else {
            CarEntity merged = getEntityManager().merge(carEntity);
            return merged.getId();
        }
    }

    default boolean existsById(Long carId) {
        return findById(carId) != null;
    }
}
