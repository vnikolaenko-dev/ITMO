package vnikolaenko.github.lab1.db.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import vnikolaenko.github.lab1.model.obj.Car;
import vnikolaenko.github.lab1.db.JpaService;
import vnikolaenko.github.lab1.model.obj.Coordinates;

import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class CarRepository {
    @Inject
    private JpaService jpaService;

    public Car findById(int id) {
        try {
            return jpaService.getEntityManager().find(Car.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to find car", e);
        }
    }

    // Поиск всех Coordinates
    public List<Car> findAll() {
        try {
            TypedQuery<Car> query = jpaService.getEntityManager()
                    .createQuery("SELECT c FROM human_being_car c", Car.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all cars", e);
        }
    }

    @Transactional
    public void deleteCarById(Integer id) {
        try {
            jpaService.getEntityManager().remove(findById(id));
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete car", e);
        }
    }

    @Transactional
    public void save(Car car) {
        try {
            EntityManager em = jpaService.getEntityManager();

            if (car.getId() == null) {
                // Новый объект — сохраняем
                em.persist(car);
            } else {
                // Существующий объект — обновляем
                em.merge(car);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save car", e);
        }
    }
}
