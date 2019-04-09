package dz.talcorp.ae.service.impl;

import static java.time.temporal.ChronoUnit.YEARS;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dz.talcorp.ae.domain.ExamenInfo;
import dz.talcorp.ae.domain.enumeration.EtatExamen;
import dz.talcorp.ae.domain.enumeration.TypeExamen;
import dz.talcorp.ae.repository.ExamenInfoRepository;
import dz.talcorp.ae.service.ExamenInfoService;
import dz.talcorp.ae.service.dto.ExamenInfoDTO;
import dz.talcorp.ae.service.mapper.ExamenInfoMapper;

/**
 * Service Implementation for managing ExamenInfo.
 */
@Service
@Transactional
public class ExamenInfoServiceImpl implements ExamenInfoService {

    /**
     * declaration des constante de code erreur
     * 
     * EK : error key S : save error D : delete error DATA : data cohesion or type
     * error CO/CR/CI : code / creno / circulation TE : type examen
     */
    private static final String EK_DATA_TE = "examen_info.EK_DATA_TE"; // error key data type error (type examen)

    private static final String EK_S_01CO = "examen_info.EK_S_01CO"; // un examen de code est deja planifier

    private static final String EK_S_02CO = "examen_info.EK_S_02CO"; // Inscription examen impossible: ce candidat a
                                                                     // deja reussi l examen de code

    private static final String EK_S_03CO = "examen_info.EK_S_03CO"; // Inscription examen impossible: ce candidat n'a
                                                                     // pas valider son examen de code

    private static final String EK_S_01CR = "examen_info.EK_S_01CR"; // un examen de creno est deja planifier

    private static final String EK_S_02CR = "examen_info.EK_S_02CR"; // Inscription examen impossible: ce candidat a
                                                                     // deja reussi l examen de creno

    private static final String EK_S_03CR = "examen_info.EK_S_03CR"; // Inscription examen impossible: ce candidat n'a
                                                                     // pas valider son examen de Creno

    private static final String EK_S_01CI = "examen_info.EK_S_01CI"; // un examen de circuilation est deja planifier

    private static final String EK_S_02CI = "examen_info.EK_S_02CI"; // Inscription examen impossible: ce candidat a
                                                                     // deja reussi l examen de circulation

    private static final String EK_D_01EE = "examen_info.EK_D_01EE"; //Supression examen impossible : ce candidat a deja
                                                                     // un examen planifier en cours

    private static final String EK_D_02EXP = "examen_info.EK_D_02EXP"; // Supression examen impossible : ce candidat a passé
                                                                       // qui date moins d une année. inscription toujours valide

    private static final String EK_E_01NS = "examen_info.EK_E_01NS"; // un examen dont le delait depassé et valider ne 
                                                                     // peut etre refait en etat en cours

    // aucune erreur detecter
    private final String NO_ERROR = "";

    private final Logger log = LoggerFactory.getLogger(ExamenInfoServiceImpl.class);

    private final ExamenInfoRepository examenInfoRepository;

    private final ExamenInfoMapper examenInfoMapper;

    public ExamenInfoServiceImpl(ExamenInfoRepository examenInfoRepository, ExamenInfoMapper examenInfoMapper) {
        this.examenInfoRepository = examenInfoRepository;
        this.examenInfoMapper = examenInfoMapper;
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
        return examenInfoMapper.toDto(examenInfo);
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
        return examenInfoRepository.findAll(pageable).map(examenInfoMapper::toDto);
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
        return examenInfoRepository.findById(id).map(examenInfoMapper::toDto);
    }

    /**
     * Delete the examenInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamenInfo : {}", id);
        examenInfoRepository.deleteById(id);
    }

    @Override
    public String checkBeforeSave(@Valid ExamenInfoDTO examenInfoDTO) {

        switch (examenInfoDTO.getType()) {
        case CODE:
            return checkCodeExamCriteria(examenInfoDTO);

        case CRENO:
            return checkCrenoExamCriteria(examenInfoDTO);

        case CIRCULATION:
            return checkCirculationExamCriteria(examenInfoDTO);

        // erreur type examen invalide
        default:
            log.error("ExamenInfoDto request param error in type of exam" + EK_DATA_TE);
            return EK_DATA_TE;

        }

    }

    /**
     * 
     * we cant subscribe a candidate to a circulation exam if he s not matching all
     * the following requirements :
     * 
     * - the candidate should have won the creno exam ==> section A
     * 
     * - the candidate is not in any current scheduled exam ==> section B
     * 
     * - the candidate has not won this exam ==> section C
     * 
     * @param examenInfoDTO
     * @return
     */
    private String checkCirculationExamCriteria(@Valid ExamenInfoDTO examenInfoDTO) {

        // section A
        Optional<ExamenInfo> examenCrenoSucceded = examenInfoRepository.findFirstByCandidatIdAndEtatAndType(
                examenInfoDTO.getCandidatId(), EtatExamen.REUSSI, TypeExamen.CRENO);
        if (!examenCrenoSucceded.isPresent())
            return EK_S_03CR;
        // section B
        Optional<ExamenInfo> examenPlanified = examenInfoRepository
                .findFirstByCandidatIdAndEtat(examenInfoDTO.getCandidatId(), EtatExamen.ENCOURS);
        if (examenPlanified.isPresent())
            switch (examenPlanified.get().getType()) {
            case CODE:
                return EK_S_01CO;
            case CRENO:
                return EK_S_01CR;
            case CIRCULATION:
                return EK_S_01CI;
            default:
                log.error("invalide data exam type exception while savng circulation exam attempt " + EK_DATA_TE);
                break;
            }
        // section C
        Optional<ExamenInfo> circulationWonExam = examenInfoRepository.findFirstByCandidatIdAndEtatAndType(
                examenInfoDTO.getCandidatId(), EtatExamen.REUSSI, TypeExamen.CIRCULATION);
        if (!circulationWonExam.isPresent())
            return EK_S_02CI;
        return NO_ERROR;
    }

    /**
     * we cant subscribe to creno exam if the candidat is not filling all of the
     * folowing requirements
     * 
     * - the candidate must have won the code exam ==> section A
     * 
     * - the candidate dont have any scheduled exams ==> section B
     * 
     * - the canddidate have not won the creno exam ==> section C
     * 
     * 
     * @param examenInfoDTO
     * @return error key
     */
    private String checkCrenoExamCriteria(@Valid ExamenInfoDTO examenInfoDTO) {

        long candidatId = examenInfoDTO.getCandidatId();
        // Section A
        Optional<ExamenInfo> successedCodeExam = examenInfoRepository.findFirstByCandidatIdAndEtatAndType(candidatId,
                EtatExamen.REUSSI, TypeExamen.CODE);
        if (!successedCodeExam.isPresent())
            return EK_S_03CO;
        // Section B
        EtatExamen etatExamen = EtatExamen.ENCOURS;
        Optional<ExamenInfo> planifiedExam = examenInfoRepository.findFirstByCandidatIdAndEtat(candidatId, etatExamen);
        ExamenInfo currentPlanifiedExam;
        if (planifiedExam.isPresent()) {
            currentPlanifiedExam = planifiedExam.get();
            switch (currentPlanifiedExam.getType()) {
            case CODE:
                return EK_S_01CO;
            case CRENO:
                return EK_S_01CR;
            case CIRCULATION:
                return EK_S_01CI;
            default:
                log.error("unknown error found in save operation EK_S_01 " + examenInfoDTO);
                break;
            }
        }
        // section C
        Optional<ExamenInfo> successCrenoExam = examenInfoRepository.findFirstByCandidatIdAndEtatAndType(candidatId,
                EtatExamen.REUSSI, TypeExamen.CRENO);
        if (successCrenoExam.isPresent())
            return EK_S_02CR;
        return NO_ERROR;
    }

    /**
     * we can subscribe to a code exam if all of the following conditions is true
     * 
     * - the candidat have not already a exam planified , etatExamen == planifier ,
     * typeExamen == any ==> A section
     * 
     * - the candidat have not already won this exam , etatExamen == reussi ,
     * typeExamen == any ==> B section
     */
    private String checkCodeExamCriteria(@Valid ExamenInfoDTO examenInfoDTO) {
        // A section
        long candidatId = examenInfoDTO.getCandidatId();
        EtatExamen etatExamen = EtatExamen.ENCOURS;
        Optional<ExamenInfo> planifiedExam = examenInfoRepository.findFirstByCandidatIdAndEtat(candidatId, etatExamen);
        ExamenInfo currentPlanifiedExam;
        if (planifiedExam.isPresent()) {
            currentPlanifiedExam = planifiedExam.get();
            switch (currentPlanifiedExam.getType()) {
            case CODE:
                return EK_S_01CO;
            case CRENO:
                return EK_S_01CR;
            case CIRCULATION:
                return EK_S_01CI;
            default:
                log.error("unknown error found in save operation EK_S_01 " + examenInfoDTO);
                break;
            }
        }

        // B section
        etatExamen = EtatExamen.REUSSI;
        Optional<ExamenInfo> succededExam = examenInfoRepository.findFirstByCandidatIdAndEtat(candidatId, etatExamen);
        ExamenInfo currentSuccededExam;
        if (succededExam.isPresent()) {
            currentSuccededExam = succededExam.get();
            switch (currentSuccededExam.getType()) {
            case CODE:
                return EK_S_02CO;
            case CRENO:
                return EK_S_02CR;
            case CIRCULATION:
                return EK_S_02CI;
            default:
                log.debug("unknown error found in save operation EK_S_02 " + examenInfoDTO);
                break;
            }
        }

        return NO_ERROR;
    }

    /**
     * we cant remove an exam before checking all the following criterias:
     * 
     * - error 1  : the exam is not a planified 
     * 
     *   - error 1.1 : the candidate have his last exam less than a year : can't remove active candidat entries
     * 
     * @param id id of deleted exam entry
     * @return the error code if there is any logic violation, empty string if it is
     *         fine to delete
     */
    @Override
    public String checkBeforeDelete(Long id) {
        // get the exam to be inspected
        Optional<ExamenInfo> concernedDeleteExam = examenInfoRepository.findById(id);
        if (concernedDeleteExam.isPresent()) {
            //  if the exam is not a planified exam we should rise an error code
            ExamenInfo deletableExam = concernedDeleteExam.get();
            if (deletableExam.getEtat() != EtatExamen.ENCOURS) {
                // other exam kinds

                final long candidatDleteExamId = deletableExam.getCandidat().getId();
                // get last candidat registred exam
                Optional<ExamenInfo> lastExam = examenInfoRepository
                        .findFirstByCandidat_IdOrderByExamen_DateExamenDesc(candidatDleteExamId);

                if(lastExam.isPresent()){
                    ExamenInfo examenInfo = lastExam.get();
                    if(examenInfo.getEtat() == EtatExamen.ENCOURS){
                        log.debug("can't remove exam entry, Exam schuddled violation : this candidate have a schudled exam "+EK_D_01EE);
                        return EK_D_01EE;
                    }else{
                        return (YEARS.between(examenInfo.getExamen().getDateExamen(), LocalDate.now()) < 1) ? EK_D_02EXP : NO_ERROR;
                    }
                }
                
            }

        }
        return NO_ERROR;
    }

    /**
     * un examen qui a ete valider ne peut etre changer a un examen en cours
     */
    @Override
    public String checkBeforeEdit(long id,EtatExamen etat) {
        ExamenInfo exam = examenInfoRepository.getOne(id);
        if(exam.getEtat() != EtatExamen.ENCOURS && etat == EtatExamen.ENCOURS) return EK_E_01NS;
        return NO_ERROR;
    }
}
