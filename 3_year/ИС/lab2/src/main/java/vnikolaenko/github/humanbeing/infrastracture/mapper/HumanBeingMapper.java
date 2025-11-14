package vnikolaenko.github.humanbeing.infrastracture.mapper;

import jakarta.inject.Inject;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import vnikolaenko.github.car.infrastracture.mapper.CarMapper;
import vnikolaenko.github.humanbeing.domen.HumanBeing;
import vnikolaenko.github.humanbeing.infrastracture.entity.HumanBeingEntity;
import vnikolaenko.github.humanbeing.resource.dto.HumanBeingDTO;

@Mapper(componentModel = "cdi",
        uses = {CarMapper.class})
public interface HumanBeingMapper {

    @Inject
    CarMapper carMapper = null;


    // Domain -> DTO
    @Mapping(source = "name", target = "name")
    @Mapping(source = "coordinates", target = "coordinates")
    @Mapping(source = "creationDate", target = "creationDate")
    @Mapping(source = "realHero", target = "realHero")
    @Mapping(source = "hasToothpick", target = "hasToothpick")
    @Mapping(source = "car", target = "car")
    @Mapping(source = "mood", target = "mood")
    @Mapping(source = "impactSpeed", target = "impactSpeed")
    @Mapping(source = "minutesOfWaiting", target = "minutesOfWaiting")
    @Mapping(source = "weaponType", target = "weaponType")
    HumanBeingDTO toDTO(HumanBeing humanBeing);

    // DTO -> Domain
    @Mapping(source = "name", target = "name")
    @Mapping(source = "coordinates", target = "coordinates")
    @Mapping(source = "creationDate", target = "creationDate")
    @Mapping(source = "realHero", target = "realHero")
    @Mapping(source = "hasToothpick", target = "hasToothpick")
    @Mapping(source = "car", target = "car")
    @Mapping(source = "mood", target = "mood")
    @Mapping(source = "impactSpeed", target = "impactSpeed")
    @Mapping(source = "minutesOfWaiting", target = "minutesOfWaiting")
    @Mapping(source = "weaponType", target = "weaponType")
    HumanBeing toDomain(HumanBeingDTO humanBeingDTO);

    // Entity -> Domain
    @Mapping(source = "name", target = "name")
    @Mapping(source = "coordinates", target = "coordinates")
    @Mapping(source = "creationDate", target = "creationDate")
    @Mapping(source = "realHero", target = "realHero")
    @Mapping(source = "hasToothpick", target = "hasToothpick")
    @Mapping(source = "car", target = "car")
    @Mapping(source = "mood", target = "mood")
    @Mapping(source = "impactSpeed", target = "impactSpeed")
    @Mapping(source = "minutesOfWaiting", target = "minutesOfWaiting")
    @Mapping(source = "weaponType", target = "weaponType")
    HumanBeing toDomain(HumanBeingEntity humanBeingEntity);

    // Domain -> Entity
    @Mapping(source = "name", target = "name")
    @Mapping(source = "coordinates", target = "coordinates")
    @Mapping(source = "creationDate", target = "creationDate")
    @Mapping(source = "realHero", target = "realHero")
    @Mapping(source = "hasToothpick", target = "hasToothpick")
    @Mapping(source = "car", target = "car")
    @Mapping(source = "mood", target = "mood")
    @Mapping(source = "impactSpeed", target = "impactSpeed")
    @Mapping(source = "minutesOfWaiting", target = "minutesOfWaiting")
    @Mapping(source = "weaponType", target = "weaponType")
    HumanBeingEntity toEntity(HumanBeing humanBeing);

    // Entity -> DTO (через Domain)
    HumanBeingDTO entityToDTO(HumanBeingEntity humanBeingEntity);

    // DTO -> Entity (через Domain)
    default HumanBeingEntity dtoToEntity(HumanBeingDTO humanBeingDTO) {
        return toEntity(toDomain(humanBeingDTO));
    }

    // Дополнительные методы для безопасного маппинга
    @Named("safeToEntity")
    default HumanBeingEntity safeToEntity(HumanBeing humanBeing) {
        if (humanBeing == null) {
            return null;
        }

        HumanBeingEntity entity = toEntity(humanBeing);

        // Дополнительная проверка обязательных полей
        if (entity.getCar() == null) {
            throw new IllegalArgumentException("Car cannot be null for HumanBeing: " + humanBeing.getName());
        }

        return entity;
    }

    // Маппинг списка с безопасной обработкой
    default java.util.List<HumanBeingEntity> toEntities(java.util.List<HumanBeing> humanBeings) {
        if (humanBeings == null) {
            return null;
        }

        return humanBeings.stream()
                .map(this::safeToEntity)
                .collect(java.util.stream.Collectors.toList());
    }
}