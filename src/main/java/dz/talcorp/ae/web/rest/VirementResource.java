package dz.talcorp.ae.web.rest;
import dz.talcorp.ae.service.VirementService;
import dz.talcorp.ae.web.rest.errors.BadRequestAlertException;
import dz.talcorp.ae.web.rest.util.HeaderUtil;
import dz.talcorp.ae.web.rest.util.PaginationUtil;
import dz.talcorp.ae.service.dto.VirementDTO;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Virement.
 */
@RestController
@RequestMapping("/api")
public class VirementResource {

    private final Logger log = LoggerFactory.getLogger(VirementResource.class);

    private static final String ENTITY_NAME = "virement";

    private final VirementService virementService;

    public VirementResource(VirementService virementService) {
        this.virementService = virementService;
    }

    /**
     * POST  /virements : Create a new virement.
     *
     * @param virementDTO the virementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new virementDTO, or with status 400 (Bad Request) if the virement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/virements")
    public ResponseEntity<VirementDTO> createVirement(@Valid @RequestBody VirementDTO virementDTO) throws URISyntaxException {
        log.debug("REST request to save Virement : {}", virementDTO);
        if (virementDTO.getId() != null) {
            throw new BadRequestAlertException("A new virement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VirementDTO result = virementService.save(virementDTO);
        return ResponseEntity.created(new URI("/api/virements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /virements : Updates an existing virement.
     *
     * @param virementDTO the virementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated virementDTO,
     * or with status 400 (Bad Request) if the virementDTO is not valid,
     * or with status 500 (Internal Server Error) if the virementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/virements")
    public ResponseEntity<VirementDTO> updateVirement(@Valid @RequestBody VirementDTO virementDTO) throws URISyntaxException {
        log.debug("REST request to update Virement : {}", virementDTO);
        if (virementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VirementDTO result = virementService.save(virementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, virementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /virements : get all the virements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of virements in body
     */
    @GetMapping("/virements")
    public ResponseEntity<List<VirementDTO>> getAllVirements(Pageable pageable) {
        log.debug("REST request to get a page of Virements");
        Page<VirementDTO> page = virementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/virements");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /virements/:id : get the "id" virement.
     *
     * @param id the id of the virementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the virementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/virements/{id}")
    public ResponseEntity<VirementDTO> getVirement(@PathVariable Long id) {
        log.debug("REST request to get Virement : {}", id);
        Optional<VirementDTO> virementDTO = virementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(virementDTO);
    }

    /**
     * DELETE  /virements/:id : delete the "id" virement.
     *
     * @param id the id of the virementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/virements/{id}")
    public ResponseEntity<Void> deleteVirement(@PathVariable Long id) {
        log.debug("REST request to delete Virement : {}", id);
        virementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/virements?query=:query : search for the virement corresponding
     * to the query.
     *
     * @param query the query of the virement search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/virements")
    public ResponseEntity<List<VirementDTO>> searchVirements(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Virements for query {}", query);
        Page<VirementDTO> page = virementService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/virements");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
