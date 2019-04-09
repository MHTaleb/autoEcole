package dz.talcorp.ae.service;

import dz.talcorp.ae.service.dto.ExamenDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import javax.validation.Valid;

/**
 * Service Interface for managing Examen.
 */
public interface ExamenService {

    /**
     * Save a examen.
     *
     * @param examenDTO the entity to save
     * @return the persisted entity
     */
    ExamenDTO save(ExamenDTO examenDTO);

    /**
     * Get all the examen.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExamenDTO> findAll(Pageable pageable);


    /**
     * Get the "id" examen.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ExamenDTO> findOne(Long id);

    /**
     * Delete the "id" examen.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	String checkBeforeSave(@Valid ExamenDTO examenDTO);
}
