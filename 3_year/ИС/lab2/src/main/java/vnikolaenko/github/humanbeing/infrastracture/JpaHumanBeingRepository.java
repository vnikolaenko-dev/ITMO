package vnikolaenko.github.humanbeing.infrastracture;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import vnikolaenko.github.car.infrastracture.entity.CarEntity;
import vnikolaenko.github.humanbeing.infrastracture.entity.HumanBeingEntity;
import vnikolaenko.github.humanbeing.valueobject.WeaponType;

import java.util.List;
import java.util.Set;



public interface JpaHumanBeingRepository extends PanacheRepository<HumanBeingEntity> {

    // Сохранение или обновление HumanBeing
    @Transactional
    default void save(HumanBeingEntity humanBeingEntity) {
        if (humanBeingEntity.getId() == null) {
            persist(humanBeingEntity);
        } else {
            getEntityManager().merge(humanBeingEntity);
        }
    }

    // Bulk save/update списка HumanBeing
    @Transactional
    default void saveAll(List<HumanBeingEntity> humanBeingEntities) {
        if (humanBeingEntities == null || humanBeingEntities.isEmpty()) {
            return;
        }

        EntityManager em = getEntityManager();
        int batchSize = 50;

        for (int i = 0; i < humanBeingEntities.size(); i++) {
            HumanBeingEntity entity = humanBeingEntities.get(i);

            if (entity.getId() == null) {
                em.persist(entity);
            } else {
                em.merge(entity);
            }

            // Батчинг: флашим и очищаем каждые batchSize entities
            if (i > 0 && i % batchSize == 0) {
                em.flush();
                em.clear();
            }
        }

        // Финальный флаш
        em.flush();
    }

    default HumanBeingEntity findByIdSafe(Long id) {
        return findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("HumanBeing not found with id: " + id));
    }

    @Transactional
    default void deleteHumanBeingById(Long id) {
        boolean deleted = deleteById(id);
        if (!deleted) {
            throw new RuntimeException("HumanBeing not found with id: " + id);
        }
    }

    default List<HumanBeingEntity> findAllWithOrder(int page, int size, String sortField, String order) {
        if (!isValidSortField(sortField)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }

        String direction = "ASC".equalsIgnoreCase(order) ? "ASC" : "DESC";
        String query = "ORDER BY " + sortField + " " + direction;
        return list(query).subList(page * size, Math.min((page + 1) * size, listAll().size()));
    }

    private boolean isValidSortField(String field) {
        Set<String> allowedFields = Set.of(
                "id", "name", "impactSpeed", "weaponType", "creationDate", "mood", "minutesOfWaiting",
                "coordinates.x", "coordinates.y",
                "car.name", "car.cool"
        );
        return allowedFields.contains(field);
    }

    default List<HumanBeingEntity> findHumanBeingWithCar(Long carId) {
        return list("car.id", carId);
    }

    default List<HumanBeingEntity> findHumanBeingWithCoordinate(Long coordinatesId) {
        return list("coordinates.id", coordinatesId);
    }

    @Transactional
    default void deleteWithImpactSpeed(Integer speed) {
        delete("impactSpeed", speed.floatValue());
    }

    @Transactional
    default Long deleteHumanBeingWithoutScraper() {
        return delete("hasToothpick", false);
    }

    default Double avgImpactSpeed() {
        Double avg = getEntityManager()
                .createQuery("SELECT AVG(h.impactSpeed) FROM HumanBeingEntity h", Double.class)
                .getSingleResult();
        return avg != null ? avg : 0.0;
    }

    default List<HumanBeingEntity> findByWeaponTypeLessThan(WeaponType weaponTypeThreshold) {
        return list("weaponType < ?1", weaponTypeThreshold);
    }

    @Transactional
    default int assignRedLadaToHumansWithoutCar() {
        List<HumanBeingEntity> humansWithoutCar = list("car IS NULL");
        for (HumanBeingEntity human : humansWithoutCar) {
            CarEntity lada = new CarEntity();
            lada.setName("Красная Lada Kalina");
            lada.setCool(true);
            human.setCar(lada);
            getEntityManager().merge(human);
        }
        return humansWithoutCar.size();
    }

    default List<HumanBeingEntity> findByName(int page, int size, String name) {
        return find("name", name)
                .page(page, size)
                .list();
    }
}