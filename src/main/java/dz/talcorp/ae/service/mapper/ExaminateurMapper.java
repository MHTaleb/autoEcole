package dz.talcorp.ae.service.mapper;

import dz.talcorp.ae.domain.*;
import dz.talcorp.ae.service.dto.ExaminateurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Examinateur and its DTO ExaminateurDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExaminateurMapper extends EntityMapper<ExaminateurDTO, Examinateur> {


    @Mapping(target = "examen", ignore = true)
    Examinateur toEntity(ExaminateurDTO examinateurDTO);

    default Examinateur fromId(Long id) {
        if (id == null) {
            return null;
        }
        Examinateur examinateur = new Examinateur();
        examinateur.setId(id);
        return examinateur;
    }
}
