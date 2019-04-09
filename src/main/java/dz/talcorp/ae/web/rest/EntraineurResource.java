package dz.talcorp.ae.web.rest;
import dz.talcorp.ae.service.EntraineurService;
import dz.talcorp.ae.web.rest.errors.BadRequestAlertException;
import dz.talcorp.ae.web.rest.util.HeaderUtil;
import dz.talcorp.ae.web.rest.util.PaginationUtil;
import dz.talcorp.ae.service.dto.EntraineurDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Entraineur.
 */
@RestController
@RequestMapping("/api")
public class EntraineurResource {

    private final Logger log = LoggerFactory.getLogger(EntraineurResource.class);

    private static final String ENTITY_NAME = "entraineur";

    private final EntraineurService entraineurService;

    public EntraineurResource(EntraineurService entraineurService) {
        this.entraineurService = entraineurService;
    }

    /**
     * POST  /entraineurs : Create a new entraineur.
     *
     * @param entraineurDTO the entraineurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entraineurDTO, or with status 400 (Bad Request) if the entraineur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entraineurs")
    public ResponseEntity<EntraineurDTO> createEntraineur(@Valid @RequestBody EntraineurDTO entraineurDTO) throws URISyntaxException {
        log.debug("REST request to save Entraineur : {}", entraineurDTO);
        if (entraineurDTO.getId() != null) {
            throw new BadRequestAlertException("A new entraineur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(entraineurService.checkUniquePhoneNumber(entraineurDTO.getTelephone())){
            throw new BadRequestAlertException("can't save new trainer: phone number unique constraint violation", ENTITY_NAME, "entraineur.EK_C_01");
        }
        EntraineurDTO result = entraineurService.save(entraineurDTO);
        return ResponseEntity.created(new URI("/api/entraineurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entraineurs : Updates an existing entraineur.
     *
     * @param entraineurDTO the entraineurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entraineurDTO,
     * or with status 400 (Bad Request) if the entraineurDTO is not valid,
     * or with status 500 (Internal Server Error) if the entraineurDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entraineurs")
    public ResponseEntity<EntraineurDTO> updateEntraineur(@Valid @RequestBody EntraineurDTO entraineurDTO) throws URISyntaxException {
        log.debug("REST request to update Entraineur : {}", entraineurDTO);
        if (entraineurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(entraineurService.checkUniquePhoneNumber(entraineurDTO.getTelephone())){
            throw new BadRequestAlertException("can't save new trainer: phone number unique constraint violation", ENTITY_NAME, "entraineur.EK_C_01");
        }
        EntraineurDTO result = entraineurService.save(entraineurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entraineurDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entraineurs : get all the entraineurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entraineurs in body
     */
    @GetMapping("/entraineurs")
    public ResponseEntity<List<EntraineurDTO>> getAllEntraineurs(Pageable pageable) {
        log.debug("REST request to get a page of Entraineurs");
        Page<EntraineurDTO> page = entraineurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entraineurs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /entraineurs/:id : get the "id" entraineur.
     *
     * @param id the id of the entraineurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entraineurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entraineurs/{id}")
    public ResponseEntity<EntraineurDTO> getEntraineur(@PathVariable Long id) {
        log.debug("REST request to get Entraineur : {}", id);
        Optional<EntraineurDTO> entraineurDTO = entraineurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entraineurDTO);
    }

    /**
     * DELETE  /entraineurs/:id : delete the "id" entraineur.
     *
     * @param id the id of the entraineurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entraineurs/{id}")
    public ResponseEntity<Void> deleteEntraineur(@PathVariable Long id) {
        log.debug("REST request to delete Entraineur : {}", id);
        String errorKey = "";
        if(!(errorKey = entraineurService.checkBeforeDelete(id)).isEmpty()){
            throw new BadRequestAlertException("can't delete trainer: delete constraint violation "+errorKey, ENTITY_NAME, errorKey);
        }
        entraineurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
