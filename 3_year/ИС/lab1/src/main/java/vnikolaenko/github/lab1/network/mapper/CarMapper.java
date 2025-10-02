package vnikolaenko.github.lab1.network.mapper;


import vnikolaenko.github.lab1.model.obj.Car;
import vnikolaenko.github.lab1.network.dto.CarDTO;

public class CarMapper {
    public static final CarMapper INSTANCE = new CarMapper();

    private CarMapper() {}

    public CarDTO toDTO(Car car) {
        if (car == null) {
            return null;
        }

        CarDTO dto = new CarDTO();
        dto.setId(car.getId());
        dto.setName(car.getName());
        dto.setCool(car.isCool());
        return dto;
    }

    public Car toEntity(CarDTO carDTO) {
        if (carDTO == null) {
            return null;
        }

        Car car = new Car();
        car.setId(carDTO.getId());
        car.setName(carDTO.getName());
        car.setCool(carDTO.isCool());
        return car;
    }
}