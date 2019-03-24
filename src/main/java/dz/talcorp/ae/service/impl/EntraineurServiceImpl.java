package dz.talcorp.ae.service.impl;

import dz.talcorp.ae.service.EntraineurService;
import dz.talcorp.ae.domain.Entraineur;
import dz.talcorp.ae.repository.EcoleRepository;
import dz.talcorp.ae.repository.EntraineurRepository;
import dz.talcorp.ae.repository.LessonRepository;
import dz.talcorp.ae.service.dto.EntraineurDTO;
import dz.talcorp.ae.service.mapper.EntraineurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Entraineur.
 */
@Service
@Transactional
public class EntraineurServiceImpl implements EntraineurService {

    private final Logger log = LoggerFactory.getLogger(EntraineurServiceImpl.class);

    private final EntraineurRepository entraineurRepository;

    private final LessonRepository lessonRepository;

    private final EcoleRepository ecoleRepository;

    private final EntraineurMapper entraineurMapper;

    public EntraineurServiceImpl(EntraineurRepository entraineurRepository, EntraineurMapper entraineurMapper,
            LessonRepository lessonRepository, EcoleRepository ecoleRepository) {
        this.entraineurRepository = entraineurRepository;
        this.entraineurMapper = entraineurMapper;
        this.lessonRepository = lessonRepository;
        this.ecoleRepository = ecoleRepository;
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
        return entraineurMapper.toDto(entraineur);
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
        return entraineurRepository.findAll(pageable).map(entraineurMapper::toDto);
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
        return entraineurRepository.findById(id).map(entraineurMapper::toDto);
    }

    /**
     * Delete the entraineur by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entraineur : {}", id);
        entraineurRepository.deleteById(id);
    }

    /**
     * check if the telephone value is not yet used in another trainer each phone
     * number is bound to one trainer (unicity, traçability)
     * 
     * @return true if the phone number is already used, otherwise false
     */
    @Override
    public boolean checkUniquePhoneNumber(String telephone) {
        return entraineurRepository.findFirstByTelephone(telephone).isPresent();
    }

    /**
     * delete validation rules :
     * 
     * - we can't delete a trainer who have any lesson entries : EK_D_01
     * 
     * - we can't delete the school owner : EK_D_02
     * 
     */
    @Override
    public String checkBeforeDelete(Long id) {
        final String ENTRAINEUR_EK_D_01 = "entraineur.EK_D_01";
        final String ENTRAINEUR_EK_D_02 = "entraineur.EK_D_02";

        if (lessonRepository.findFirstByEntraineurId(id).isPresent()) {
            return ENTRAINEUR_EK_D_01;
        }

        if (ecoleRepository.findFirstByPresidentId(id).isPresent()) {
            return ENTRAINEUR_EK_D_02;
        }
        final String NO_ERROR = "";
        return NO_ERROR;
    }
}
