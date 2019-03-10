package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.Lesson;

import java.time.Instant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lesson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    long countByDateLessonBetween(Instant min,Instant max);
}
