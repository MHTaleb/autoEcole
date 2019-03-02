package dz.talcorp.ae.service;

import dz.talcorp.ae.service.dto.ExaminateurDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Examinateur.
 */
public interface ExaminateurService {

    /**
     * Save a examinateur.
     *
     * @param examinateurDTO the entity to save
     * @return the persisted entity
     */
    ExaminateurDTO save(ExaminateurDTO examinateurDTO);

    /**
     * Get all the examinateurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExaminateurDTO> findAll(Pageable pageable);


    /**
     * Get the "id" examinateur.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ExaminateurDTO> findOne(Long id);

    /**
     * Delete the "id" examinateur.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the examinateur corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExaminateurDTO> search(String query, Pageable pageable);
}
