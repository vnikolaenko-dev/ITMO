package vnikolaenko.github.lab1.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import vnikolaenko.github.lab1.db.repository.CoordinatesRepository;
import vnikolaenko.github.lab1.db.repository.HumanBeingRepository;
import vnikolaenko.github.lab1.model.obj.Coordinates;
import vnikolaenko.github.lab1.model.obj.HumanBeing;

import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class CoordinatesService {
    @Inject
    private CoordinatesRepository coordinatesRepository;
    @Inject
    private HumanBeingRepository humanBeingRepository;

    public List<Coordinates> getAll() {
        return coordinatesRepository.findAll();
    }


    public boolean delete(Integer id) {
        List<HumanBeing> humanBeings = humanBeingRepository.findHumanBeingWithCoordinate(id);
        if (!humanBeings.isEmpty()) {
            return false;
        } else {
            coordinatesRepository.deleteCoordinatesById(id);
            return true;
        }
    }
}
