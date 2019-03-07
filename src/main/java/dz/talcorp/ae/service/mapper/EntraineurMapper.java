package dz.talcorp.ae.service.mapper;

import dz.talcorp.ae.domain.*;
import dz.talcorp.ae.service.dto.EntraineurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Entraineur and its DTO EntraineurDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface EntraineurMapper extends EntityMapper<EntraineurDTO, Entraineur> {

    @Mapping(source = "compte.id", target = "compteId")
    @Mapping(source = "compte.login", target = "compteLogin")
    EntraineurDTO toDto(Entraineur entraineur);

    @Mapping(source = "compteId", target = "compte")
    @Mapping(target = "lessons", ignore = true)
    Entraineur toEntity(EntraineurDTO entraineurDTO);

    default Entraineur fromId(Long id) {
        if (id == null) {
            return null;
        }
        Entraineur entraineur = new Entraineur();
        entraineur.setId(id);
        return entraineur;
    }
}
