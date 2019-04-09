package dz.talcorp.ae.web.rest;
import dz.talcorp.ae.service.CandidatService;
import dz.talcorp.ae.web.rest.errors.BadRequestAlertException;
import dz.talcorp.ae.web.rest.errors.CustomParameterizedException;
import dz.talcorp.ae.web.rest.util.HeaderUtil;
import dz.talcorp.ae.web.rest.util.PaginationUtil;
import dz.talcorp.ae.service.dto.CandidatDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Candidat.
 */
@RestController
@RequestMapping("/api")
public class CandidatResource {

    private final Logger log = LoggerFactory.getLogger(CandidatResource.class);

    private static final String ENTITY_NAME = "candidat";

    private final CandidatService candidatService;

    public CandidatResource(CandidatService candidatService) {
        this.candidatService = candidatService;
    }

    /**
     * POST  /candidats : Create a new candidat.
     *
     * @param candidatDTO the candidatDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidatDTO, or with status 400 (Bad Request) if the candidat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidats")
    public ResponseEntity<CandidatDTO> createCandidat(@Valid @RequestBody CandidatDTO candidatDTO) throws URISyntaxException {
        log.debug("REST request to save Candidat : {}", candidatDTO);
        if (candidatDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        /**
         * checking unique nid
         */
        if(candidatService.checkNID(candidatDTO.getNid(),candidatDTO.getId())){
            throw new CustomParameterizedException("candidat.EK_CA_01", candidatDTO.getNid());
        }
        /**
         * Check candidat is 17 years old or more
         */
        if(candidatService.checkCandidatMature(candidatDTO.getDateNaissance())){
            throw new BadRequestAlertException("a new candidat must be mature 17 years old or more", ENTITY_NAME, "candidat.EK_CA_02");
        }
        CandidatDTO result = candidatService.save(candidatDTO);
        return ResponseEntity.created(new URI("/api/candidats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidats : Updates an existing candidat.
     *
     * @param candidatDTO the candidatDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidatDTO,
     * or with status 400 (Bad Request) if the candidatDTO is not valid,
     * or with status 500 (Internal Server Error) if the candidatDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidats")
    public ResponseEntity<CandidatDTO> updateCandidat(@Valid @RequestBody CandidatDTO candidatDTO) throws URISyntaxException {
        log.debug("REST request to update Candidat : {}", candidatDTO);
        if (candidatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        /**
         * checking unique nid
         */
        if(candidatService.checkNID(candidatDTO.getNid(),candidatDTO.getId())){
            throw new CustomParameterizedException("candidat.EK_CA_01", candidatDTO.getNid());
        }
        /**
         * Check candidat is 17 years old or more
         */
        if(candidatService.checkCandidatMature(candidatDTO.getDateNaissance())){
            throw new BadRequestAlertException("a new candidat must be mature 17 years old or more", ENTITY_NAME, "candidat.EK_CA_02");
        }
        CandidatDTO result = candidatService.save(candidatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidatDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidats : get all the candidats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of candidats in body
     */
    @GetMapping("/candidats")
    public ResponseEntity<List<CandidatDTO>> getAllCandidats(Pageable pageable) {
        log.debug("REST request to get a page of Candidats");
        Page<CandidatDTO> page = candidatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/candidats");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /candidats/:id : get the "id" candidat.
     *
     * @param id the id of the candidatDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidatDTO, or with status 404 (Not Found)
     */
    @GetMapping("/candidats/{id}")
    public ResponseEntity<CandidatDTO> getCandidat(@PathVariable Long id) {
        log.debug("REST request to get Candidat : {}", id);
        Optional<CandidatDTO> candidatDTO = candidatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidatDTO);
    }

    /**
     * DELETE  /candidats/:id : delete the "id" candidat.
     * 
     * this need to be updated
     * 
     * a failed delete should have some reporting to failure cause
     * 
     * 1- relation with a lesson
     * 2- relation with an exam
     *
     * this should not be implemented in the service delete cuz maybe in futur I will add force delete option 
     * and to avoid any conflict with jhipster merging code when using git
     * 
     * @param id the id of the candidatDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidats/{id}")
    public ResponseEntity<Void> deleteCandidat(@PathVariable Long id) {
        log.debug("REST request to delete Candidat : {}", id);
        if (candidatService.checkLessonRelation(id)) {
            throw new BadRequestAlertException("delete impossible of candidat due to relation with lesson entity", ENTITY_NAME, "candidat.EK_DLR_01");
        }
        if (candidatService.checkExamRelation(id)) {
            throw new BadRequestAlertException("delete impossible of candidat due to relation with examenInfo entity", ENTITY_NAME, "candidat.EK_DER_02");
        }
        candidatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
