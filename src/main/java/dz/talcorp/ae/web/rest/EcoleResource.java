package dz.talcorp.ae.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dz.talcorp.ae.service.EcoleService;
import dz.talcorp.ae.service.dto.EcoleDTO;
import dz.talcorp.ae.web.rest.errors.BadRequestAlertException;
import dz.talcorp.ae.web.rest.util.HeaderUtil;
import dz.talcorp.ae.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Ecole.
 * 
 * in the next version, I want to link every candidat and user to the current loged in school, this way 
 * I will enable multi school management, however in this version one school by instance
 * lightwigh version
 * 
 */
@RestController
@RequestMapping("/api")
public class EcoleResource {

    private final Logger log = LoggerFactory.getLogger(EcoleResource.class);

    private static final String ENTITY_NAME = "ecole";

    private final EcoleService ecoleService;

    public EcoleResource(EcoleService ecoleService) {
        this.ecoleService = ecoleService;
    }

    /**
     * POST  /ecoles : Create a new ecole.
     *
     * @param ecoleDTO the ecoleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ecoleDTO, or with status 400 (Bad Request) if the ecole has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ecoles")
    public ResponseEntity<EcoleDTO> createEcole(@Valid @RequestBody EcoleDTO ecoleDTO) throws URISyntaxException {
        log.debug("REST request to save Ecole : {}", ecoleDTO);
        if (ecoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new ecole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // check titreEcole unicity
        if(ecoleService.checkTitreUnique(ecoleDTO.getTitreEcole())){
            throw new BadRequestAlertException("ecole creation error : ecole titre duplicate", ENTITY_NAME, "ecole.EK_C_01");
        }
        EcoleDTO result = ecoleService.save(ecoleDTO);
        return ResponseEntity.created(new URI("/api/ecoles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ecoles : Updates an existing ecole.
     *
     * @param ecoleDTO the ecoleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ecoleDTO,
     * or with status 400 (Bad Request) if the ecoleDTO is not valid,
     * or with status 500 (Internal Server Error) if the ecoleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ecoles")
    public ResponseEntity<EcoleDTO> updateEcole(@Valid @RequestBody EcoleDTO ecoleDTO) throws URISyntaxException {
        log.debug("REST request to update Ecole : {}", ecoleDTO);
        if (ecoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        // check titreEcole unicity
        if(ecoleService.checkTitreUnique(ecoleDTO.getTitreEcole())){
            throw new BadRequestAlertException("ecole creation error : ecole titre duplicate", ENTITY_NAME, "ecole.EK_C_01");
        }
        EcoleDTO result = ecoleService.save(ecoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ecoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ecoles : get all the ecoles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ecoles in body
     */
    @GetMapping("/ecoles")
    public ResponseEntity<List<EcoleDTO>> getAllEcoles(Pageable pageable) {
        log.debug("REST request to get a page of Ecoles");
        Page<EcoleDTO> page = ecoleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ecoles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /ecoles/:id : get the "id" ecole.
     *
     * @param id the id of the ecoleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ecoleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ecoles/{id}")
    public ResponseEntity<EcoleDTO> getEcole(@PathVariable Long id) {
        log.debug("REST request to get Ecole : {}", id);
        Optional<EcoleDTO> ecoleDTO = ecoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ecoleDTO);
    }

    /**
     * DELETE  /ecoles/:id : delete the "id" ecole.
     *
     * @param id the id of the ecoleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ecoles/{id}")
    public ResponseEntity<Void> deleteEcole(@PathVariable Long id) {
        log.debug("REST request to delete Ecole : {}", id);
        String errorKey = ecoleService.checkEcoleBeforeDelete(id);
        if(!errorKey.isEmpty()){
            throw new BadRequestAlertException("ecole supression error : ecole is not empty of data", ENTITY_NAME, errorKey);
        }
        ecoleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
