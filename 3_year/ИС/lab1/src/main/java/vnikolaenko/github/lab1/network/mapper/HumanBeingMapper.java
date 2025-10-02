package vnikolaenko.github.lab1.network.mapper;

import vnikolaenko.github.lab1.model.obj.HumanBeing;
import vnikolaenko.github.lab1.model.obj.Coordinates;
import vnikolaenko.github.lab1.model.obj.Car;
import vnikolaenko.github.lab1.network.dto.HumanBeingDTO;
import vnikolaenko.github.lab1.network.dto.CoordinatesDTO;
import vnikolaenko.github.lab1.network.dto.CarDTO;

public class HumanBeingMapper {
    public static final HumanBeingMapper INSTANCE = new HumanBeingMapper();

    private final CarMapper carMapper = CarMapper.INSTANCE;
    private final CoordinatesMapper coordinatesMapper = CoordinatesMapper.INSTANCE;

    private HumanBeingMapper() {}

    public HumanBeingDTO toDTO(HumanBeing humanBeing) {
        if (humanBeing == null) {
            return null;
        }

        HumanBeingDTO dto = new HumanBeingDTO();
        dto.setId(humanBeing.getId());
        dto.setName(humanBeing.getName());
        dto.setCoordinates(coordinatesMapper.toDTO(humanBeing.getCoordinates()));
        dto.setCreationDate(humanBeing.getCreationDate());
        dto.setRealHero(humanBeing.isRealHero());
        dto.setHasToothpick(humanBeing.getHasToothpick());
        dto.setCar(carMapper.toDTO(humanBeing.getCar()));
        dto.setMood(humanBeing.getMood());
        dto.setImpactSpeed(humanBeing.getImpactSpeed());
        dto.setMinutesOfWaiting(humanBeing.getMinutesOfWaiting());
        dto.setWeaponType(humanBeing.getWeaponType());

        return dto;
    }

    public HumanBeing toEntity(HumanBeingDTO humanBeingDTO) {
        if (humanBeingDTO == null) {
            return null;
        }

        HumanBeing humanBeing = new HumanBeing();
        humanBeing.setId(humanBeingDTO.getId());
        humanBeing.setName(humanBeingDTO.getName());
        humanBeing.setCoordinates(coordinatesMapper.toEntity(humanBeingDTO.getCoordinates()));
        humanBeing.setCreationDate(humanBeingDTO.getCreationDate());
        humanBeing.setRealHero(humanBeingDTO.isRealHero());
        humanBeing.setHasToothpick(humanBeingDTO.getHasToothpick());
        humanBeing.setCar(carMapper.toEntity(humanBeingDTO.getCar()));
        humanBeing.setMood(humanBeingDTO.getMood());
        humanBeing.setImpactSpeed(humanBeingDTO.getImpactSpeed());
        humanBeing.setMinutesOfWaiting(humanBeingDTO.getMinutesOfWaiting());
        humanBeing.setWeaponType(humanBeingDTO.getWeaponType());

        return humanBeing;
    }
}