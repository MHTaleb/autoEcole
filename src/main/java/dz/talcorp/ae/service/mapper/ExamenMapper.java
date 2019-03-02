package dz.talcorp.ae.service.mapper;

import dz.talcorp.ae.domain.*;
import dz.talcorp.ae.service.dto.ExamenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Examen and its DTO ExamenDTO.
 */
@Mapper(componentModel = "spring", uses = {CarMapper.class, ExaminateurMapper.class})
public interface ExamenMapper extends EntityMapper<ExamenDTO, Examen> {

    @Mapping(source = "voiture.id", target = "voitureId")
    @Mapping(source = "examinateur.id", target = "examinateurId")
    ExamenDTO toDto(Examen examen);

    @Mapping(target = "examenInfos", ignore = true)
    @Mapping(source = "voitureId", target = "voiture")
    @Mapping(source = "examinateurId", target = "examinateur")
    Examen toEntity(ExamenDTO examenDTO);

    default Examen fromId(Long id) {
        if (id == null) {
            return null;
        }
        Examen examen = new Examen();
        examen.setId(id);
        return examen;
    }
}
