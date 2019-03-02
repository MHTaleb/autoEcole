package dz.talcorp.ae.service;

import dz.talcorp.ae.service.dto.EntraineurDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Entraineur.
 */
public interface EntraineurService {

    /**
     * Save a entraineur.
     *
     * @param entraineurDTO the entity to save
     * @return the persisted entity
     */
    EntraineurDTO save(EntraineurDTO entraineurDTO);

    /**
     * Get all the entraineurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EntraineurDTO> findAll(Pageable pageable);


    /**
     * Get the "id" entraineur.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EntraineurDTO> findOne(Long id);

    /**
     * Delete the "id" entraineur.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the entraineur corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EntraineurDTO> search(String query, Pageable pageable);
}
