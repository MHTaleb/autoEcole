package dz.talcorp.ae.service.impl;

import dz.talcorp.ae.service.ExamenInfoService;
import dz.talcorp.ae.domain.ExamenInfo;
import dz.talcorp.ae.repository.ExamenInfoRepository;
import dz.talcorp.ae.repository.search.ExamenInfoSearchRepository;
import dz.talcorp.ae.service.dto.ExamenInfoDTO;
import dz.talcorp.ae.service.mapper.ExamenInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ExamenInfo.
 */
@Service
@Transactional
public class ExamenInfoServiceImpl implements ExamenInfoService {

    private final Logger log = LoggerFactory.getLogger(ExamenInfoServiceImpl.class);

    private final ExamenInfoRepository examenInfoRepository;

    private final ExamenInfoMapper examenInfoMapper;

    private final ExamenInfoSearchRepository examenInfoSearchRepository;

    public ExamenInfoServiceImpl(ExamenInfoRepository examenInfoRepository, ExamenInfoMapper examenInfoMapper, ExamenInfoSearchRepository examenInfoSearchRepository) {
        this.examenInfoRepository = examenInfoRepository;
        this.examenInfoMapper = examenInfoMapper;
        this.examenInfoSearchRepository = examenInfoSearchRepository;
    }

    /**
     * Save a examenInfo.
     *
     * @param examenInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExamenInfoDTO save(ExamenInfoDTO examenInfoDTO) {
        log.debug("Request to save ExamenInfo : {}", examenInfoDTO);
        ExamenInfo examenInfo = examenInfoMapper.toEntity(examenInfoDTO);
        examenInfo = examenInfoRepository.save(examenInfo);
        ExamenInfoDTO result = examenInfoMapper.toDto(examenInfo);
        examenInfoSearchRepository.save(examenInfo);
        return result;
    }

    /**
     * Get all the examenInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExamenInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamenInfos");
        return examenInfoRepository.findAll(pageable)
            .map(examenInfoMapper::toDto);
    }


    /**
     * Get one examenInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExamenInfoDTO> findOne(Long id) {
        log.debug("Request to get ExamenInfo : {}", id);
        return examenInfoRepository.findById(id)
            .map(examenInfoMapper::toDto);
    }

    /**
     * Delete the examenInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamenInfo : {}", id);        examenInfoRepository.deleteById(id);
        examenInfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the examenInfo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExamenInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ExamenInfos for query {}", query);
        return examenInfoSearchRepository.search(queryStringQuery(query), pageable)
            .map(examenInfoMapper::toDto);
    }
}
