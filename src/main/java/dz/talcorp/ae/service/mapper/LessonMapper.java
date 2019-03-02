package dz.talcorp.ae.service.mapper;

import dz.talcorp.ae.domain.*;
import dz.talcorp.ae.service.dto.LessonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lesson and its DTO LessonDTO.
 */
@Mapper(componentModel = "spring", uses = {CandidatMapper.class, CarMapper.class, EntraineurMapper.class})
public interface LessonMapper extends EntityMapper<LessonDTO, Lesson> {

    @Mapping(source = "candidat.id", target = "candidatId")
    @Mapping(source = "voiture.id", target = "voitureId")
    @Mapping(source = "entraineur.id", target = "entraineurId")
    LessonDTO toDto(Lesson lesson);

    @Mapping(source = "candidatId", target = "candidat")
    @Mapping(source = "voitureId", target = "voiture")
    @Mapping(source = "entraineurId", target = "entraineur")
    Lesson toEntity(LessonDTO lessonDTO);

    default Lesson fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lesson lesson = new Lesson();
        lesson.setId(id);
        return lesson;
    }
}
