package dz.talcorp.ae.service.mapper;

import dz.talcorp.ae.domain.*;
import dz.talcorp.ae.service.dto.ExamenInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExamenInfo and its DTO ExamenInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {ExamenMapper.class, CandidatMapper.class})
public interface ExamenInfoMapper extends EntityMapper<ExamenInfoDTO, ExamenInfo> {

    @Mapping(source = "examen.id", target = "examenId")
    @Mapping(source = "candidat.id", target = "candidatId")
    ExamenInfoDTO toDto(ExamenInfo examenInfo);

    @Mapping(source = "examenId", target = "examen")
    @Mapping(source = "candidatId", target = "candidat")
    ExamenInfo toEntity(ExamenInfoDTO examenInfoDTO);

    default ExamenInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExamenInfo examenInfo = new ExamenInfo();
        examenInfo.setId(id);
        return examenInfo;
    }
}
