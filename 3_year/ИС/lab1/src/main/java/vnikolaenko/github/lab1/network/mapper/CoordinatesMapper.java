package vnikolaenko.github.lab1.network.mapper;

import vnikolaenko.github.lab1.model.obj.Coordinates;
import vnikolaenko.github.lab1.network.dto.CoordinatesDTO;

public class CoordinatesMapper {
    public static final CoordinatesMapper INSTANCE = new CoordinatesMapper();

    private CoordinatesMapper() {}

    public CoordinatesDTO toDTO(Coordinates coordinates) {
        if (coordinates == null) {
            return null;
        }

        CoordinatesDTO dto = new CoordinatesDTO();
        dto.setId(coordinates.getId());
        dto.setX(coordinates.getX());
        dto.setY(coordinates.getY());
        return dto;
    }

    public Coordinates toEntity(CoordinatesDTO coordinatesDTO) {
        if (coordinatesDTO == null) {
            return null;
        }

        Coordinates coordinates = new Coordinates();
        coordinates.setId(coordinatesDTO.getId());
        coordinates.setX(coordinatesDTO.getX());
        coordinates.setY(coordinatesDTO.getY());
        return coordinates;
    }
}