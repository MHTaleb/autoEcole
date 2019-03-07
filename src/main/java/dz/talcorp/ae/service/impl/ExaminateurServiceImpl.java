package dz.talcorp.ae.service.impl;

import dz.talcorp.ae.service.ExaminateurService;
import dz.talcorp.ae.domain.Examinateur;
import dz.talcorp.ae.repository.ExaminateurRepository;
import dz.talcorp.ae.service.dto.ExaminateurDTO;
import dz.talcorp.ae.service.mapper.ExaminateurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Examinateur.
 */
@Service
@Transactional
public class ExaminateurServiceImpl implements ExaminateurService {

    private final Logger log = LoggerFactory.getLogger(ExaminateurServiceImpl.class);

    private final ExaminateurRepository examinateurRepository;

    private final ExaminateurMapper examinateurMapper;

    public ExaminateurServiceImpl(ExaminateurRepository examinateurRepository, ExaminateurMapper examinateurMapper) {
        this.examinateurRepository = examinateurRepository;
        this.examinateurMapper = examinateurMapper;
    }

    /**
     * Save a examinateur.
     *
     * @param examinateurDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExaminateurDTO save(ExaminateurDTO examinateurDTO) {
        log.debug("Request to save Examinateur : {}", examinateurDTO);
        Examinateur examinateur = examinateurMapper.toEntity(examinateurDTO);
        examinateur = examinateurRepository.save(examinateur);
        return examinateurMapper.toDto(examinateur);
    }

    /**
     * Get all the examinateurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExaminateurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Examinateurs");
        return examinateurRepository.findAll(pageable)
            .map(examinateurMapper::toDto);
    }


    /**
     * Get one examinateur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExaminateurDTO> findOne(Long id) {
        log.debug("Request to get Examinateur : {}", id);
        return examinateurRepository.findById(id)
            .map(examinateurMapper::toDto);
    }

    /**
     * Delete the examinateur by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Examinateur : {}", id);        examinateurRepository.deleteById(id);
    }
}
