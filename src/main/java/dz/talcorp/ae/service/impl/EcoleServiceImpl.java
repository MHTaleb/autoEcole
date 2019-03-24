package dz.talcorp.ae.service.impl;

import dz.talcorp.ae.service.EcoleService;
import dz.talcorp.ae.domain.Ecole;
import dz.talcorp.ae.repository.CandidatRepository;
import dz.talcorp.ae.repository.EcoleRepository;
import dz.talcorp.ae.repository.EntraineurRepository;
import dz.talcorp.ae.repository.ExamenRepository;
import dz.talcorp.ae.repository.LessonRepository;
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

    private final LessonRepository lessonRepository;

    private final EntraineurRepository entraineurRepository;

    private final CandidatRepository candidatRepository;

    private final ExamenRepository examenRepository;

    private final EcoleMapper ecoleMapper;

    public EcoleServiceImpl(EcoleRepository ecoleRepository, EcoleMapper ecoleMapper, LessonRepository lessonRepository,
            EntraineurRepository entraineurRepository, CandidatRepository candidatRepository,
            ExamenRepository examenRepository) {
        this.ecoleRepository = ecoleRepository;
        this.ecoleMapper = ecoleMapper;
        this.lessonRepository = lessonRepository;
        this.entraineurRepository = entraineurRepository;
        this.candidatRepository = candidatRepository;
        this.examenRepository = examenRepository;
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
        return ecoleRepository.findAll(pageable).map(ecoleMapper::toDto);
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
        return ecoleRepository.findById(id).map(ecoleMapper::toDto);
    }

    /**
     * Delete the ecole by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ecole : {}", id);
        ecoleRepository.deleteById(id);
    }

    /**
     * search for an existing school with titreEcole
     * 
     * @return true if a school with titreEcole already exists
     */
    @Override
    public boolean checkTitreUnique(String titreEcole) {
        return ecoleRepository.findFirstByTitreEcole(titreEcole).isPresent();
    }

    /**
     * check that the school is removable EK_R_01 : -we cant remove a school that
     * have at least a : teacher, student , or any data on exams
     */
    @Override
    public String checkEcoleBeforeDelete(Long id) {
        final String EK_R_01 = "ecole.EK_R_01"; // teacher
        final String EK_R_02 = "ecole.EK_R_02"; // student
        final String EK_R_03 = "ecole.EK_R_03"; // exams
        final String EK_R_04 = "ecole.EK_R_04"; // lesson

        if (entraineurRepository.count() > 0) {
            return EK_R_01;
        }
        if (candidatRepository.count() > 0) {
            return EK_R_02;
        }
        if (examenRepository.count() > 0) {
            return EK_R_03;
        }
        if (lessonRepository.count() > 0) {
            return EK_R_04;
        }

        final String No_ERROR = "";
        return No_ERROR;
    }
}
