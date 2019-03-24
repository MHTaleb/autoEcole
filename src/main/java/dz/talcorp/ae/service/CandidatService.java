package dz.talcorp.ae.service;

import dz.talcorp.ae.service.dto.CandidatDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

/**
 * Service Interface for managing Candidat.
 */
public interface CandidatService {

    /**
     * Save a candidat.
     *
     * @param candidatDTO the entity to save
     * @return the persisted entity
     */
    CandidatDTO save(CandidatDTO candidatDTO);

    /**
     * Get all the candidats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CandidatDTO> findAll(Pageable pageable);


    /**
     * Get the "id" candidat.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CandidatDTO> findOne(Long id);

    /**
     * Delete the "id" candidat.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * check NID unicity
     */
	boolean checkNID(String nid);

    /**
     * check age maturity
     */
	boolean checkCandidatMature(LocalDate birthDate);

    /**
     * check if there is any lesson relation with candidat id
     */
	boolean checkLessonRelation(Long id);

    /**
     * check if there is any exam relation with candidat id
     */
	boolean checkExamRelation(Long id);
}
