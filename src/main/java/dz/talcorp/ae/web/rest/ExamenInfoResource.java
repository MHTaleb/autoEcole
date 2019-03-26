package dz.talcorp.ae.web.rest;
import dz.talcorp.ae.service.ExamenInfoService;
import dz.talcorp.ae.web.rest.errors.BadRequestAlertException;
import dz.talcorp.ae.web.rest.util.HeaderUtil;
import dz.talcorp.ae.web.rest.util.PaginationUtil;
import dz.talcorp.ae.service.dto.ExamenInfoDTO;
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
 * REST controller for managing ExamenInfo.
 */
@RestController
@RequestMapping("/api")
public class ExamenInfoResource {

    private final Logger log = LoggerFactory.getLogger(ExamenInfoResource.class);

    private static final String ENTITY_NAME = "examenInfo";

    private final ExamenInfoService examenInfoService;

    public ExamenInfoResource(ExamenInfoService examenInfoService) {
        this.examenInfoService = examenInfoService;
    }

    /**
     * POST  /examen-infos : Create a new examenInfo.
     *
     * @param examenInfoDTO the examenInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examenInfoDTO, or with status 400 (Bad Request) if the examenInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/examen-infos")
    public ResponseEntity<ExamenInfoDTO> createExamenInfo(@Valid @RequestBody ExamenInfoDTO examenInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ExamenInfo : {}", examenInfoDTO);
        if (examenInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new examenInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        String errorKey="";
        if((errorKey = examenInfoService.checkBeforeSave(examenInfoDTO)).isEmpty()){
            throw new BadRequestAlertException("add exam entry constraint violation code : "+errorKey, ENTITY_NAME, errorKey);
        }
        ExamenInfoDTO result = examenInfoService.save(examenInfoDTO);
        return ResponseEntity.created(new URI("/api/examen-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /examen-infos : Updates an existing examenInfo.
     *
     * @param examenInfoDTO the examenInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examenInfoDTO,
     * or with status 400 (Bad Request) if the examenInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the examenInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/examen-infos")
    public ResponseEntity<ExamenInfoDTO> updateExamenInfo(@Valid @RequestBody ExamenInfoDTO examenInfoDTO) throws URISyntaxException {
        log.debug("REST request to update ExamenInfo : {}", examenInfoDTO);
        if (examenInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        String errorKey="";
        if((errorKey = examenInfoService.checkBeforeSave(examenInfoDTO)).isEmpty()){
            throw new BadRequestAlertException("add exam entry constraint violation code : "+errorKey, ENTITY_NAME, errorKey);
        }
        ExamenInfoDTO result = examenInfoService.save(examenInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examenInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /examen-infos : get all the examenInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examenInfos in body
     */
    @GetMapping("/examen-infos")
    public ResponseEntity<List<ExamenInfoDTO>> getAllExamenInfos(Pageable pageable) {
        log.debug("REST request to get a page of ExamenInfos");
        Page<ExamenInfoDTO> page = examenInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/examen-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /examen-infos/:id : get the "id" examenInfo.
     *
     * @param id the id of the examenInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examenInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/examen-infos/{id}")
    public ResponseEntity<ExamenInfoDTO> getExamenInfo(@PathVariable Long id) {
        log.debug("REST request to get ExamenInfo : {}", id);
        Optional<ExamenInfoDTO> examenInfoDTO = examenInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examenInfoDTO);
    }

    /**
     * DELETE  /examen-infos/:id : delete the "id" examenInfo.
     *
     * @param id the id of the examenInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/examen-infos/{id}")
    public ResponseEntity<Void> deleteExamenInfo(@PathVariable Long id) {
        log.debug("REST request to delete ExamenInfo : {}", id);
        String errorKey="";
        if((errorKey = examenInfoService.checkBeforeDelete(id)).isEmpty()){
            throw new BadRequestAlertException("add exam entry constraint violation code : "+errorKey, ENTITY_NAME, errorKey);
        }
        examenInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
