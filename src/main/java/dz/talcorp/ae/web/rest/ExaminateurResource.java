package dz.talcorp.ae.web.rest;
import dz.talcorp.ae.service.ExaminateurService;
import dz.talcorp.ae.web.rest.errors.BadRequestAlertException;
import dz.talcorp.ae.web.rest.util.HeaderUtil;
import dz.talcorp.ae.web.rest.util.PaginationUtil;
import dz.talcorp.ae.service.dto.ExaminateurDTO;
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
 * REST controller for managing Examinateur.
 */
@RestController
@RequestMapping("/api")
public class ExaminateurResource {

    private final Logger log = LoggerFactory.getLogger(ExaminateurResource.class);

    private static final String ENTITY_NAME = "examinateur";

    private final ExaminateurService examinateurService;

    public ExaminateurResource(ExaminateurService examinateurService) {
        this.examinateurService = examinateurService;
    }

    /**
     * POST  /examinateurs : Create a new examinateur.
     *
     * @param examinateurDTO the examinateurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examinateurDTO, or with status 400 (Bad Request) if the examinateur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/examinateurs")
    public ResponseEntity<ExaminateurDTO> createExaminateur(@Valid @RequestBody ExaminateurDTO examinateurDTO) throws URISyntaxException {
        log.debug("REST request to save Examinateur : {}", examinateurDTO);
        if (examinateurDTO.getId() != null) {
            throw new BadRequestAlertException("A new examinateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExaminateurDTO result = examinateurService.save(examinateurDTO);
        return ResponseEntity.created(new URI("/api/examinateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /examinateurs : Updates an existing examinateur.
     *
     * @param examinateurDTO the examinateurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examinateurDTO,
     * or with status 400 (Bad Request) if the examinateurDTO is not valid,
     * or with status 500 (Internal Server Error) if the examinateurDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/examinateurs")
    public ResponseEntity<ExaminateurDTO> updateExaminateur(@Valid @RequestBody ExaminateurDTO examinateurDTO) throws URISyntaxException {
        log.debug("REST request to update Examinateur : {}", examinateurDTO);
        if (examinateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExaminateurDTO result = examinateurService.save(examinateurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examinateurDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /examinateurs : get all the examinateurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examinateurs in body
     */
    @GetMapping("/examinateurs")
    public ResponseEntity<List<ExaminateurDTO>> getAllExaminateurs(Pageable pageable) {
        log.debug("REST request to get a page of Examinateurs");
        Page<ExaminateurDTO> page = examinateurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/examinateurs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /examinateurs/:id : get the "id" examinateur.
     *
     * @param id the id of the examinateurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examinateurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/examinateurs/{id}")
    public ResponseEntity<ExaminateurDTO> getExaminateur(@PathVariable Long id) {
        log.debug("REST request to get Examinateur : {}", id);
        Optional<ExaminateurDTO> examinateurDTO = examinateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examinateurDTO);
    }

    /**
     * DELETE  /examinateurs/:id : delete the "id" examinateur.
     *
     * @param id the id of the examinateurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/examinateurs/{id}")
    public ResponseEntity<Void> deleteExaminateur(@PathVariable Long id) {
        log.debug("REST request to delete Examinateur : {}", id);
        examinateurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/examinateurs?query=:query : search for the examinateur corresponding
     * to the query.
     *
     * @param query the query of the examinateur search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/examinateurs")
    public ResponseEntity<List<ExaminateurDTO>> searchExaminateurs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Examinateurs for query {}", query);
        Page<ExaminateurDTO> page = examinateurService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/examinateurs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
