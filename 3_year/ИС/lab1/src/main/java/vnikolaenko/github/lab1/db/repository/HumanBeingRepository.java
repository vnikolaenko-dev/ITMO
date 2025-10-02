package vnikolaenko.github.lab1.db.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import vnikolaenko.github.lab1.model.obj.Car;
import vnikolaenko.github.lab1.model.obj.HumanBeing;
import vnikolaenko.github.lab1.db.JpaService;
import vnikolaenko.github.lab1.model.obj.WeaponType;

import java.util.List;
import java.util.Set;

@ApplicationScoped
@NoArgsConstructor
public class HumanBeingRepository {
    @Inject
    private JpaService jpaService;

    // Сохранение или обновление HumanBeing
    @Transactional
    public void save(HumanBeing humanBeing) {
        try {
            EntityManager em = jpaService.getEntityManager();

            if (humanBeing.getId() == null) {
                // Новый объект — сохраняем
                em.persist(humanBeing);
            } else {
                // Существующий объект — обновляем
                em.merge(humanBeing);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to save HumanBeing", e);
        }
    }

    public HumanBeing findById(int id) {
        try {
            return jpaService.getEntityManager().find(HumanBeing.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void deleteHumanBeingById(Integer id) {
        try {
            jpaService.getEntityManager().remove(findById(id));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to delete car", e);
        }
    }

    public List<HumanBeing> findAllWithOrder(int page, int size, String arg, String order) {
        try {
            if (!isValidSortField(arg)) {
                throw new IllegalArgumentException("Invalid sort field: " + arg);
            }

            // Определяем направление сортировки
            String direction = "ASC";
            if ("desc".equalsIgnoreCase(order)) {
                direction = "DESC";
            }

            TypedQuery<HumanBeing> query = jpaService.getEntityManager()
                    .createQuery("SELECT h FROM human_being h ORDER BY h." + arg + " " + direction, HumanBeing.class);
            query.setFirstResult(page * size); // смещение
            query.setMaxResults(size);         // размер страницы
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find users with pagination", e);
        }
    }

    private boolean isValidSortField(String field) {
        // Разрешенные поля для сортировки
        Set<String> allowedFields = Set.of("id", "name", "impactSpeed", "weaponType", "creationDate", "mood", "minutesOfWaiting",
                "coordinates.x", "coordinates.y",
                "car.name", "car.cool");
        return allowedFields.contains(field);
    }


    public List<HumanBeing> findHumanBeingWithCar(Integer car_id) {
        try {
            TypedQuery<HumanBeing> query = jpaService.getEntityManager()
                    .createQuery("SELECT h FROM human_being h where h.car.id = :car_id", HumanBeing.class);
            query.setParameter("car_id", car_id);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all users with car", e);
        }
    }

    public List<HumanBeing> findHumanBeingWithCoordinate(Integer coordinates_id) {
        try {
            TypedQuery<HumanBeing> query = jpaService.getEntityManager()
                    .createQuery("SELECT h FROM human_being h where h.coordinates.id = :coordinates_id", HumanBeing.class);
            query.setParameter("coordinates_id", coordinates_id);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all users with coordinates", e);
        }
    }

    @Transactional
    public boolean deleteWithImpactSpeed(Integer speed) {
        try {
            // Удаляем сущность HumanBeing с заданным impactSpeed
            Query query = jpaService.getEntityManager()
                    .createQuery("DELETE FROM human_being h WHERE h.impactSpeed = :speed")
                    .setParameter("speed", Float.valueOf(speed));
            int deleted = query.executeUpdate();
            return deleted > 0;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete HumanBeing by speed", e);
        }
    }

    @Transactional
    public Integer deleteHumanBeingWithoutScraper() {
        try {
            // Удаляем сущность HumanBeing с заданным impactSpeed
            Query query = jpaService.getEntityManager()
                    .createQuery("DELETE FROM human_being h WHERE h.hasToothpick = false ");
            return query.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete HumanBeing without scraper", e);
        }
    }

    public Double avgImpactSpeed() {
        try {
            // Получаем среднее значение impactSpeed
            Double avg = jpaService.getEntityManager()
                    .createQuery("SELECT AVG(h.impactSpeed) FROM human_being h", Double.class)
                    .getSingleResult();
            return avg != null ? avg : 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate average impact speed", e);
        }
    }

    // Вернуть массив объектов, значение поля weaponType которых меньше заданного
    public List<HumanBeing> findByWeaponTypeLessThan(WeaponType weaponTypeThreshold) {
        try {
            TypedQuery<HumanBeing> query = jpaService.getEntityManager()
                    .createQuery("SELECT h FROM human_being h WHERE h.weaponType < :weaponType", HumanBeing.class);
            query.setParameter("weaponType", weaponTypeThreshold);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to find HumanBeing with weaponType < " + weaponTypeThreshold, e);
        }
    }

    // Пересадить всех героев без автомобиля на красные "Lada Kalina"
    public int assignRedLadaToHumansWithoutCar() {
        try {
            // JPQL не поддерживает создание новых объектов напрямую в UPDATE, поэтому используем подход с merge
            List<HumanBeing> humansWithoutCar = jpaService.getEntityManager()
                    .createQuery("SELECT h FROM human_being h WHERE h.car IS NULL", HumanBeing.class)
                    .getResultList();

            for (HumanBeing human : humansWithoutCar) {
                Car lada = new Car();
                lada.setName("Красная Lada Kalina");
                lada.setCool(true);
                human.setCar(lada);
                jpaService.getEntityManager().merge(human);
            }

            return humansWithoutCar.size();
        } catch (Exception e) {
            throw new RuntimeException("Failed to assign red Lada to humans without car", e);
        }
    }


    public List<HumanBeing> findByName(int page, int size, String name) {
        try {
            TypedQuery<HumanBeing> query = jpaService.getEntityManager()
                    .createQuery("SELECT h FROM human_being h WHERE h.name = :name", HumanBeing.class);
            query.setParameter("name", name);
            query.setFirstResult(page * size); // смещение
            query.setMaxResults(size);         // размер страницы
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find users with pagination", e);
        }
    }
}
