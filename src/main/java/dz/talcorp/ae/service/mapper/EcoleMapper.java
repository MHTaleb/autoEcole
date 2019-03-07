package dz.talcorp.ae.service.mapper;

import dz.talcorp.ae.domain.*;
import dz.talcorp.ae.service.dto.EcoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ecole and its DTO EcoleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EcoleMapper extends EntityMapper<EcoleDTO, Ecole> {



    default Ecole fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ecole ecole = new Ecole();
        ecole.setId(id);
        return ecole;
    }
}
