package dz.talcorp.ae.service.impl;

import dz.talcorp.ae.service.CandidatService;
import dz.talcorp.ae.domain.Candidat;
import dz.talcorp.ae.repository.CandidatRepository;
import dz.talcorp.ae.repository.search.CandidatSearchRepository;
import dz.talcorp.ae.service.dto.CandidatDTO;
import dz.talcorp.ae.service.mapper.CandidatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Candidat.
 */
@Service
@Transactional
public class CandidatServiceImpl implements CandidatService {

    private final Logger log = LoggerFactory.getLogger(CandidatServiceImpl.class);

    private final CandidatRepository candidatRepository;

    private final CandidatMapper candidatMapper;

    private final CandidatSearchRepository candidatSearchRepository;

    public CandidatServiceImpl(CandidatRepository candidatRepository, CandidatMapper candidatMapper, CandidatSearchRepository candidatSearchRepository) {
        this.candidatRepository = candidatRepository;
        this.candidatMapper = candidatMapper;
        this.candidatSearchRepository = candidatSearchRepository;
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
        CandidatDTO result = candidatMapper.toDto(candidat);
        candidatSearchRepository.save(candidat);
        return result;
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
        return candidatRepository.findAll(pageable)
            .map(candidatMapper::toDto);
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
        return candidatRepository.findById(id)
            .map(candidatMapper::toDto);
    }

    /**
     * Delete the candidat by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Candidat : {}", id);        candidatRepository.deleteById(id);
        candidatSearchRepository.deleteById(id);
    }

    /**
     * Search for the candidat corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CandidatDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Candidats for query {}", query);
        return candidatSearchRepository.search(queryStringQuery(query), pageable)
            .map(candidatMapper::toDto);
    }
}
