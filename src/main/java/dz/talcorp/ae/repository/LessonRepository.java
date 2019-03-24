package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.Candidat;
import dz.talcorp.ae.domain.Lesson;
import dz.talcorp.ae.domain.enumeration.TypeLesson;
import dz.talcorp.ae.service.dto.EntraineurDTO;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Lesson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    // count of lessons between two dates
    long countByDateLessonBetween(Instant min, Instant max);

    // count of lesson of specific type between two dates
    long countByDateLessonBetweenAndTypeLessonEquals(Instant min, Instant max, TypeLesson typeLesson);

    // count of lessons for a specific student (candidat) for a specific type
    // between specific dates
    long countByDateLessonBetweenAndTypeLessonEqualsAndCandidatEquals(Instant min, Instant max, TypeLesson typeLesson,
            Candidat candidat);

    // count of lessons for a specific candidat between two dates
    long countByDateLessonBetweenAndCandidatEquals(Instant min, Instant max, Candidat candidat);

    // check if a candidat have a lesson relation
    Optional<Lesson> findFirstByCandidatId(long id);

    // check if a trainer have a lesson relation
	Optional<Lesson> findFirstByEntraineurId(Long id);
}
