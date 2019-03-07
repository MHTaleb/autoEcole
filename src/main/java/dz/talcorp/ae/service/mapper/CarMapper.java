package dz.talcorp.ae.service.mapper;

import dz.talcorp.ae.domain.*;
import dz.talcorp.ae.service.dto.CarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Car and its DTO CarDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarMapper extends EntityMapper<CarDTO, Car> {


    @Mapping(target = "examen", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    Car toEntity(CarDTO carDTO);

    default Car fromId(Long id) {
        if (id == null) {
            return null;
        }
        Car car = new Car();
        car.setId(id);
        return car;
    }
}
