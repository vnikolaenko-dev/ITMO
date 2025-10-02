package vnikolaenko.github.lab1.db.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import vnikolaenko.github.lab1.model.obj.Car;
import vnikolaenko.github.lab1.model.obj.Coordinates;
import vnikolaenko.github.lab1.db.JpaService;

import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class CoordinatesRepository {
    @Inject
    private JpaService jpaService;

    public Coordinates findById(int id) {
        try {
            return jpaService.getEntityManager().find(Coordinates.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all users", e);
        }
    }

    // Поиск всех Coordinates
    public List<Coordinates> findAll() {
        try {
            TypedQuery<Coordinates> query = jpaService.getEntityManager()
                    .createQuery("SELECT c FROM human_being_coordinates c", Coordinates.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all coordinates", e);
        }
    }

    @Transactional
    public void deleteCoordinatesById(Integer id) {
        try {
            jpaService.getEntityManager().remove(findById(id));
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete coordinates", e);
        }
    }

    @Transactional
    public void save(Coordinates coordinates) {
        try {
            EntityManager em = jpaService.getEntityManager();

            if (coordinates.getId() == null) {
                // Новый объект — сохраняем
                em.persist(coordinates);
            } else {
                // Существующий объект — обновляем
                em.merge(coordinates);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save coordinates", e);
        }
    }
}
