package dz.talcorp.ae.service.mapper;

import dz.talcorp.ae.domain.*;
import dz.talcorp.ae.service.dto.EcoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ecole and its DTO EcoleDTO.
 */
@Mapper(componentModel = "spring", uses = {EntraineurMapper.class})
public interface EcoleMapper extends EntityMapper<EcoleDTO, Ecole> {

    @Mapping(source = "president.id", target = "presidentId")
    @Mapping(source = "president.nom", target = "presidentNom")
    EcoleDTO toDto(Ecole ecole);

    @Mapping(source = "presidentId", target = "president")
    Ecole toEntity(EcoleDTO ecoleDTO);

    default Ecole fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ecole ecole = new Ecole();
        ecole.setId(id);
        return ecole;
    }
}
