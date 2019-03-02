package dz.talcorp.ae.service;

import dz.talcorp.ae.service.dto.VirementDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Virement.
 */
public interface VirementService {

    /**
     * Save a virement.
     *
     * @param virementDTO the entity to save
     * @return the persisted entity
     */
    VirementDTO save(VirementDTO virementDTO);

    /**
     * Get all the virements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VirementDTO> findAll(Pageable pageable);


    /**
     * Get the "id" virement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VirementDTO> findOne(Long id);

    /**
     * Delete the "id" virement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the virement corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VirementDTO> search(String query, Pageable pageable);
}
