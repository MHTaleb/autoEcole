package dz.talcorp.ae.service.impl;

import dz.talcorp.ae.service.ExamenService;
import dz.talcorp.ae.domain.Examen;
import dz.talcorp.ae.repository.ExamenRepository;
import dz.talcorp.ae.repository.search.ExamenSearchRepository;
import dz.talcorp.ae.service.dto.ExamenDTO;
import dz.talcorp.ae.service.mapper.ExamenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Examen.
 */
@Service
@Transactional
public class ExamenServiceImpl implements ExamenService {

    private final Logger log = LoggerFactory.getLogger(ExamenServiceImpl.class);

    private final ExamenRepository examenRepository;

    private final ExamenMapper examenMapper;

    private final ExamenSearchRepository examenSearchRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository, ExamenMapper examenMapper, ExamenSearchRepository examenSearchRepository) {
        this.examenRepository = examenRepository;
        this.examenMapper = examenMapper;
        this.examenSearchRepository = examenSearchRepository;
    }

    /**
     * Save a examen.
     *
     * @param examenDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExamenDTO save(ExamenDTO examenDTO) {
        log.debug("Request to save Examen : {}", examenDTO);
        Examen examen = examenMapper.toEntity(examenDTO);
        examen = examenRepository.save(examen);
        ExamenDTO result = examenMapper.toDto(examen);
        examenSearchRepository.save(examen);
        return result;
    }

    /**
     * Get all the examen.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExamenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Examen");
        return examenRepository.findAll(pageable)
            .map(examenMapper::toDto);
    }


    /**
     * Get one examen by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExamenDTO> findOne(Long id) {
        log.debug("Request to get Examen : {}", id);
        return examenRepository.findById(id)
            .map(examenMapper::toDto);
    }

    /**
     * Delete the examen by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Examen : {}", id);        examenRepository.deleteById(id);
        examenSearchRepository.deleteById(id);
    }

    /**
     * Search for the examen corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExamenDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Examen for query {}", query);
        return examenSearchRepository.search(queryStringQuery(query), pageable)
            .map(examenMapper::toDto);
    }
}
