package vnikolaenko.github.car.infrastracture.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vnikolaenko.github.car.domain.Car;
import vnikolaenko.github.car.infrastracture.entity.CarEntity;
import vnikolaenko.github.car.resource.dto.CarDto;

@Mapper(componentModel = "cdi")
public interface CarMapper {

    // Domain Model -> DTO
    @Mapping(source = "cool", target = "cool")
    @Mapping(source = "name", target = "name")
    CarDto toDTO(Car car);

    // DTO -> Domain Model
    @Mapping(source = "cool", target = "cool")
    @Mapping(source = "name", target = "name")
    Car toDomain(CarDto carDTO);

    // Domain Model -> Entity
    @Mapping(source = "cool", target = "cool")
    @Mapping(source = "name", target = "name")
    CarEntity toEntity(Car car);

    // Entity -> Domain Model
    @Mapping(source = "cool", target = "cool")
    @Mapping(source = "name", target = "name")
    Car toDomain(CarEntity carEntity);

    // Entity -> DTO
    @Mapping(source = "cool", target = "cool")
    @Mapping(source = "name", target = "name")
    CarDto entityToDTO(CarEntity carEntity);
}