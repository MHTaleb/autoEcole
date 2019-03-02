package dz.talcorp.ae.service.mapper;

import dz.talcorp.ae.domain.*;
import dz.talcorp.ae.service.dto.VirementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Virement and its DTO VirementDTO.
 */
@Mapper(componentModel = "spring", uses = {CandidatMapper.class})
public interface VirementMapper extends EntityMapper<VirementDTO, Virement> {

    @Mapping(source = "candidat.id", target = "candidatId")
    VirementDTO toDto(Virement virement);

    @Mapping(source = "candidatId", target = "candidat")
    Virement toEntity(VirementDTO virementDTO);

    default Virement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Virement virement = new Virement();
        virement.setId(id);
        return virement;
    }
}
