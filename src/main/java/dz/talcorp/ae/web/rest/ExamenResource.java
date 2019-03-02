package dz.talcorp.ae.web.rest;
import dz.talcorp.ae.service.ExamenService;
import dz.talcorp.ae.web.rest.errors.BadRequestAlertException;
import dz.talcorp.ae.web.rest.util.HeaderUtil;
import dz.talcorp.ae.web.rest.util.PaginationUtil;
import dz.talcorp.ae.service.dto.ExamenDTO;
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
 * REST controller for managing Examen.
 */
@RestController
@RequestMapping("/api")
public class ExamenResource {

    private final Logger log = LoggerFactory.getLogger(ExamenResource.class);

    private static final String ENTITY_NAME = "examen";

    private final ExamenService examenService;

    public ExamenResource(ExamenService examenService) {
        this.examenService = examenService;
    }

    /**
     * POST  /examen : Create a new examen.
     *
     * @param examenDTO the examenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examenDTO, or with status 400 (Bad Request) if the examen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/examen")
    public ResponseEntity<ExamenDTO> createExamen(@Valid @RequestBody ExamenDTO examenDTO) throws URISyntaxException {
        log.debug("REST request to save Examen : {}", examenDTO);
        if (examenDTO.getId() != null) {
            throw new BadRequestAlertException("A new examen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamenDTO result = examenService.save(examenDTO);
        return ResponseEntity.created(new URI("/api/examen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /examen : Updates an existing examen.
     *
     * @param examenDTO the examenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examenDTO,
     * or with status 400 (Bad Request) if the examenDTO is not valid,
     * or with status 500 (Internal Server Error) if the examenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/examen")
    public ResponseEntity<ExamenDTO> updateExamen(@Valid @RequestBody ExamenDTO examenDTO) throws URISyntaxException {
        log.debug("REST request to update Examen : {}", examenDTO);
        if (examenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExamenDTO result = examenService.save(examenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /examen : get all the examen.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examen in body
     */
    @GetMapping("/examen")
    public ResponseEntity<List<ExamenDTO>> getAllExamen(Pageable pageable) {
        log.debug("REST request to get a page of Examen");
        Page<ExamenDTO> page = examenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/examen");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /examen/:id : get the "id" examen.
     *
     * @param id the id of the examenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/examen/{id}")
    public ResponseEntity<ExamenDTO> getExamen(@PathVariable Long id) {
        log.debug("REST request to get Examen : {}", id);
        Optional<ExamenDTO> examenDTO = examenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examenDTO);
    }

    /**
     * DELETE  /examen/:id : delete the "id" examen.
     *
     * @param id the id of the examenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/examen/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable Long id) {
        log.debug("REST request to delete Examen : {}", id);
        examenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/examen?query=:query : search for the examen corresponding
     * to the query.
     *
     * @param query the query of the examen search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/examen")
    public ResponseEntity<List<ExamenDTO>> searchExamen(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Examen for query {}", query);
        Page<ExamenDTO> page = examenService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/examen");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
