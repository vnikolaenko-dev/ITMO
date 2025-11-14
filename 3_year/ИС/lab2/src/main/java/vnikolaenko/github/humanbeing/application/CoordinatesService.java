package vnikolaenko.github.humanbeing.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vnikolaenko.github.humanbeing.valueobject.Coordinates;
import vnikolaenko.github.shared.exception.ObjectConnectionException;
import vnikolaenko.github.humanbeing.infrastracture.entity.HumanBeingEntity;
import vnikolaenko.github.humanbeing.infrastracture.JpaHumanBeingRepository;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class CoordinatesService {
    @Inject
    JpaHumanBeingRepository jpaHumanBeingRepository;

    public List<Coordinates> getAll() {
        List<HumanBeingEntity> entities = jpaHumanBeingRepository.findAll().list();
        Set<Coordinates> coordinatesSet = new HashSet<>();

        for (HumanBeingEntity entity : entities) {
            if (entity.getCoordinates() != null) {
                coordinatesSet.add(entity.getCoordinates());
            }
        }

        return new ArrayList<>(coordinatesSet);
    }
}
