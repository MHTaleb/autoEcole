package dz.talcorp.ae.service;

import dz.talcorp.ae.domain.enumeration.EtatExamen;
import dz.talcorp.ae.service.dto.ExamenInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import javax.validation.Valid;

/**
 * Service Interface for managing ExamenInfo.
 */
public interface ExamenInfoService {

    /**
     * Save a examenInfo.
     *
     * @param examenInfoDTO the entity to save
     * @return the persisted entity
     */
    ExamenInfoDTO save(ExamenInfoDTO examenInfoDTO);

    /**
     * Get all the examenInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExamenInfoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" examenInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ExamenInfoDTO> findOne(Long id);

    /**
     * Delete the "id" examenInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	String checkBeforeSave(@Valid ExamenInfoDTO examenInfoDTO);

	String checkBeforeDelete(Long id);

	String checkBeforeEdit(long id,EtatExamen etat);
}
