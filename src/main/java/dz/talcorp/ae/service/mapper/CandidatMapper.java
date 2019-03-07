package dz.talcorp.ae.service.mapper;

import dz.talcorp.ae.domain.*;
import dz.talcorp.ae.service.dto.CandidatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Candidat and its DTO CandidatDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CandidatMapper extends EntityMapper<CandidatDTO, Candidat> {


    @Mapping(target = "examenInfos", ignore = true)
    @Mapping(target = "virements", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    Candidat toEntity(CandidatDTO candidatDTO);

    default Candidat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Candidat candidat = new Candidat();
        candidat.setId(id);
        return candidat;
    }
}
