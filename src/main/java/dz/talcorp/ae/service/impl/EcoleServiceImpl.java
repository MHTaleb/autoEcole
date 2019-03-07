package dz.talcorp.ae.service.impl;

import dz.talcorp.ae.service.EcoleService;
import dz.talcorp.ae.domain.Ecole;
import dz.talcorp.ae.repository.EcoleRepository;
import dz.talcorp.ae.service.dto.EcoleDTO;
import dz.talcorp.ae.service.mapper.EcoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Ecole.
 */
@Service
@Transactional
public class EcoleServiceImpl implements EcoleService {

    private final Logger log = LoggerFactory.getLogger(EcoleServiceImpl.class);

    private final EcoleRepository ecoleRepository;

    private final EcoleMapper ecoleMapper;

    public EcoleServiceImpl(EcoleRepository ecoleRepository, EcoleMapper ecoleMapper) {
        this.ecoleRepository = ecoleRepository;
        this.ecoleMapper = ecoleMapper;
    }

    /**
     * Save a ecole.
     *
     * @param ecoleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EcoleDTO save(EcoleDTO ecoleDTO) {
        log.debug("Request to save Ecole : {}", ecoleDTO);
        Ecole ecole = ecoleMapper.toEntity(ecoleDTO);
        ecole = ecoleRepository.save(ecole);
        return ecoleMapper.toDto(ecole);
    }

    /**
     * Get all the ecoles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EcoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ecoles");
        return ecoleRepository.findAll(pageable)
            .map(ecoleMapper::toDto);
    }


    /**
     * Get one ecole by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EcoleDTO> findOne(Long id) {
        log.debug("Request to get Ecole : {}", id);
        return ecoleRepository.findById(id)
            .map(ecoleMapper::toDto);
    }

    /**
     * Delete the ecole by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ecole : {}", id);        ecoleRepository.deleteById(id);
    }
}
