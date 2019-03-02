package dz.talcorp.ae.service.impl;

import dz.talcorp.ae.service.VirementService;
import dz.talcorp.ae.domain.Virement;
import dz.talcorp.ae.repository.VirementRepository;
import dz.talcorp.ae.repository.search.VirementSearchRepository;
import dz.talcorp.ae.service.dto.VirementDTO;
import dz.talcorp.ae.service.mapper.VirementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Virement.
 */
@Service
@Transactional
public class VirementServiceImpl implements VirementService {

    private final Logger log = LoggerFactory.getLogger(VirementServiceImpl.class);

    private final VirementRepository virementRepository;

    private final VirementMapper virementMapper;

    private final VirementSearchRepository virementSearchRepository;

    public VirementServiceImpl(VirementRepository virementRepository, VirementMapper virementMapper, VirementSearchRepository virementSearchRepository) {
        this.virementRepository = virementRepository;
        this.virementMapper = virementMapper;
        this.virementSearchRepository = virementSearchRepository;
    }

    /**
     * Save a virement.
     *
     * @param virementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VirementDTO save(VirementDTO virementDTO) {
        log.debug("Request to save Virement : {}", virementDTO);
        Virement virement = virementMapper.toEntity(virementDTO);
        virement = virementRepository.save(virement);
        VirementDTO result = virementMapper.toDto(virement);
        virementSearchRepository.save(virement);
        return result;
    }

    /**
     * Get all the virements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VirementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Virements");
        return virementRepository.findAll(pageable)
            .map(virementMapper::toDto);
    }


    /**
     * Get one virement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VirementDTO> findOne(Long id) {
        log.debug("Request to get Virement : {}", id);
        return virementRepository.findById(id)
            .map(virementMapper::toDto);
    }

    /**
     * Delete the virement by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Virement : {}", id);        virementRepository.deleteById(id);
        virementSearchRepository.deleteById(id);
    }

    /**
     * Search for the virement corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VirementDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Virements for query {}", query);
        return virementSearchRepository.search(queryStringQuery(query), pageable)
            .map(virementMapper::toDto);
    }
}
