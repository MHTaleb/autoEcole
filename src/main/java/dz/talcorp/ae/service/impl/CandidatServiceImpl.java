package dz.talcorp.ae.service.impl;

import static java.time.temporal.ChronoUnit.YEARS;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dz.talcorp.ae.domain.Candidat;
import dz.talcorp.ae.repository.CandidatRepository;
import dz.talcorp.ae.repository.ExamenInfoRepository;
import dz.talcorp.ae.repository.LessonRepository;
import dz.talcorp.ae.service.CandidatService;
import dz.talcorp.ae.service.dto.CandidatDTO;
import dz.talcorp.ae.service.mapper.CandidatMapper;

/**
 * Service Implementation for managing Candidat.
 */
@Service
@Transactional
public class CandidatServiceImpl implements CandidatService {

    private final Logger log = LoggerFactory.getLogger(CandidatServiceImpl.class);

    private final CandidatRepository candidatRepository;

    private final LessonRepository lessonRepository;

    private final ExamenInfoRepository examenInfoRepository;

    private final CandidatMapper candidatMapper;

    public CandidatServiceImpl(CandidatRepository candidatRepository, CandidatMapper candidatMapper,
            LessonRepository lessonRepository, ExamenInfoRepository examenInfoRepository) {
        this.candidatRepository = candidatRepository;
        this.candidatMapper = candidatMapper;
        this.lessonRepository = lessonRepository;
        this.examenInfoRepository = examenInfoRepository;
    }

    /**
     * Save a candidat.
     *
     * @param candidatDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CandidatDTO save(CandidatDTO candidatDTO) {
        log.debug("Request to save Candidat : {}", candidatDTO);
        Candidat candidat = candidatMapper.toEntity(candidatDTO);
        candidat = candidatRepository.save(candidat);
        return candidatMapper.toDto(candidat);
    }

    /**
     * Get all the candidats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CandidatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Candidats");
        return candidatRepository.findAll(pageable).map(candidatMapper::toDto);
    }

    /**
     * Get one candidat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CandidatDTO> findOne(Long id) {
        log.debug("Request to get Candidat : {}", id);
        return candidatRepository.findById(id).map(candidatMapper::toDto);
    }

    /**
     * Delete the candidat by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Candidat : {}", id);
        candidatRepository.deleteById(id);
    }

    /**
     * check if candidat nid is unique
     * 
     * @return true if not unique
     */
    @Override
    public boolean checkNID(String nid, long cid) {
        log.debug("Request to check if candidat NID is fine (unique)");
        return candidatRepository.findFirstByNidAndIdNot(nid,cid).isPresent();
    }

    /**
     * check age is 17 or more to validate user subscrubtion
     */
    @Override
    public boolean checkCandidatMature(LocalDate birthDate) {
        return YEARS.between(birthDate, LocalDate.now()) >= 17;
    }

    /**
     * check if the current candidat have already a lesson
     */
    @Override
    public boolean checkLessonRelation(Long id) {
        return lessonRepository.findFirstByCandidatId(id).isPresent();
    }

    /**
     * check if the current candidat have already an exam relation
     */
    @Override
    public boolean checkExamRelation(Long id) {
        return examenInfoRepository.findFirstByCandidatId(id).isPresent();
    }
}
