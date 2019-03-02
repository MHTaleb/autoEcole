package dz.talcorp.ae.service.impl;

import dz.talcorp.ae.service.EntraineurService;
import dz.talcorp.ae.domain.Entraineur;
import dz.talcorp.ae.repository.EntraineurRepository;
import dz.talcorp.ae.repository.search.EntraineurSearchRepository;
import dz.talcorp.ae.service.dto.EntraineurDTO;
import dz.talcorp.ae.service.mapper.EntraineurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Entraineur.
 */
@Service
@Transactional
public class EntraineurServiceImpl implements EntraineurService {

    private final Logger log = LoggerFactory.getLogger(EntraineurServiceImpl.class);

    private final EntraineurRepository entraineurRepository;

    private final EntraineurMapper entraineurMapper;

    private final EntraineurSearchRepository entraineurSearchRepository;

    public EntraineurServiceImpl(EntraineurRepository entraineurRepository, EntraineurMapper entraineurMapper, EntraineurSearchRepository entraineurSearchRepository) {
        this.entraineurRepository = entraineurRepository;
        this.entraineurMapper = entraineurMapper;
        this.entraineurSearchRepository = entraineurSearchRepository;
    }

    /**
     * Save a entraineur.
     *
     * @param entraineurDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EntraineurDTO save(EntraineurDTO entraineurDTO) {
        log.debug("Request to save Entraineur : {}", entraineurDTO);
        Entraineur entraineur = entraineurMapper.toEntity(entraineurDTO);
        entraineur = entraineurRepository.save(entraineur);
        EntraineurDTO result = entraineurMapper.toDto(entraineur);
        entraineurSearchRepository.save(entraineur);
        return result;
    }

    /**
     * Get all the entraineurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EntraineurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entraineurs");
        return entraineurRepository.findAll(pageable)
            .map(entraineurMapper::toDto);
    }


    /**
     * Get one entraineur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EntraineurDTO> findOne(Long id) {
        log.debug("Request to get Entraineur : {}", id);
        return entraineurRepository.findById(id)
            .map(entraineurMapper::toDto);
    }

    /**
     * Delete the entraineur by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entraineur : {}", id);        entraineurRepository.deleteById(id);
        entraineurSearchRepository.deleteById(id);
    }

    /**
     * Search for the entraineur corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EntraineurDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Entraineurs for query {}", query);
        return entraineurSearchRepository.search(queryStringQuery(query), pageable)
            .map(entraineurMapper::toDto);
    }
}
