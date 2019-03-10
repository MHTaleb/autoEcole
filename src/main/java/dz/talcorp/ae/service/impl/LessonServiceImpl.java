package dz.talcorp.ae.service.impl;

import dz.talcorp.ae.service.LessonService;
import dz.talcorp.ae.domain.Lesson;
import dz.talcorp.ae.repository.LessonRepository;
import dz.talcorp.ae.service.dto.LessonDTO;
import dz.talcorp.ae.service.mapper.LessonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

import javax.validation.Valid;

/**
 * Service Implementation for managing Lesson.
 */
@Service
@Transactional
public class LessonServiceImpl implements LessonService {

    private final Logger log = LoggerFactory.getLogger(LessonServiceImpl.class);

    private final LessonRepository lessonRepository;

    private final LessonMapper lessonMapper;

    public LessonServiceImpl(LessonRepository lessonRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
    }

    /**
     * Save a lesson.
     *
     * @param lessonDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LessonDTO save(LessonDTO lessonDTO) {
        log.debug("Request to save Lesson : {}", lessonDTO);
        Lesson lesson = lessonMapper.toEntity(lessonDTO);
        lesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(lesson);
    }

    /**
     * Get all the lessons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LessonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lessons");
        return lessonRepository.findAll(pageable).map(lessonMapper::toDto);
    }

    /**
     * Get one lesson by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LessonDTO> findOne(Long id) {
        log.debug("Request to get Lesson : {}", id);
        return lessonRepository.findById(id).map(lessonMapper::toDto);
    }

    /**
     * Delete the lesson by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lesson : {}", id);
        lessonRepository.deleteById(id);
    }


    /**
     * Check if lesson have enought time and dont colide with other lessons
     * this checks if no lesson starts 45 min befor this one and no lesson start 45min after this one
     * 
     * @param lessonDTO the desired creation lesson
     */
    @Override
    public boolean checkLessonTime(@Valid LessonDTO lessonDTO) {
        log.debug("Request to check Lesson valid time: {}", lessonDTO);
        Lesson lesson = lessonMapper.toEntity(lessonDTO);
        Instant min = lesson.getDateLesson().minusSeconds(2700);
        Instant max = lesson.getDateLesson().plusSeconds(2700);
        boolean isTimeEnaugh = lessonRepository.countByDateLessonBetween(min, max) == 0;
        return isTimeEnaugh;
        
    }
}
