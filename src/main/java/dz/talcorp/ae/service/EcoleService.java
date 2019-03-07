package dz.talcorp.ae.service;

import dz.talcorp.ae.service.dto.EcoleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Ecole.
 */
public interface EcoleService {

    /**
     * Save a ecole.
     *
     * @param ecoleDTO the entity to save
     * @return the persisted entity
     */
    EcoleDTO save(EcoleDTO ecoleDTO);

    /**
     * Get all the ecoles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EcoleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ecole.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EcoleDTO> findOne(Long id);

    /**
     * Delete the "id" ecole.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
