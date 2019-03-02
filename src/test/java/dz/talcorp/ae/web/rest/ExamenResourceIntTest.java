package dz.talcorp.ae.web.rest;

import dz.talcorp.ae.AutoEcoleApp;

import dz.talcorp.ae.domain.Examen;
import dz.talcorp.ae.domain.Examinateur;
import dz.talcorp.ae.repository.ExamenRepository;
import dz.talcorp.ae.repository.search.ExamenSearchRepository;
import dz.talcorp.ae.service.ExamenService;
import dz.talcorp.ae.service.dto.ExamenDTO;
import dz.talcorp.ae.service.mapper.ExamenMapper;
import dz.talcorp.ae.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static dz.talcorp.ae.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExamenResource REST controller.
 *
 * @see ExamenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoEcoleApp.class)
public class ExamenResourceIntTest {

    private static final LocalDate DEFAULT_DATE_EXAMEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXAMEN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ExamenRepository examenRepository;

    @Autowired
    private ExamenMapper examenMapper;

    @Autowired
    private ExamenService examenService;

    /**
     * This repository is mocked in the dz.talcorp.ae.repository.search test package.
     *
     * @see dz.talcorp.ae.repository.search.ExamenSearchRepositoryMockConfiguration
     */
    @Autowired
    private ExamenSearchRepository mockExamenSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restExamenMockMvc;

    private Examen examen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamenResource examenResource = new ExamenResource(examenService);
        this.restExamenMockMvc = MockMvcBuilders.standaloneSetup(examenResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examen createEntity(EntityManager em) {
        Examen examen = new Examen()
            .dateExamen(DEFAULT_DATE_EXAMEN);
        // Add required entity
        Examinateur examinateur = ExaminateurResourceIntTest.createEntity(em);
        em.persist(examinateur);
        em.flush();
        examen.setExaminateur(examinateur);
        return examen;
    }

    @Before
    public void initTest() {
        examen = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamen() throws Exception {
        int databaseSizeBeforeCreate = examenRepository.findAll().size();

        // Create the Examen
        ExamenDTO examenDTO = examenMapper.toDto(examen);
        restExamenMockMvc.perform(post("/api/examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examenDTO)))
            .andExpect(status().isCreated());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeCreate + 1);
        Examen testExamen = examenList.get(examenList.size() - 1);
        assertThat(testExamen.getDateExamen()).isEqualTo(DEFAULT_DATE_EXAMEN);

        // Validate the Examen in Elasticsearch
        verify(mockExamenSearchRepository, times(1)).save(testExamen);
    }

    @Test
    @Transactional
    public void createExamenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examenRepository.findAll().size();

        // Create the Examen with an existing ID
        examen.setId(1L);
        ExamenDTO examenDTO = examenMapper.toDto(examen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamenMockMvc.perform(post("/api/examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeCreate);

        // Validate the Examen in Elasticsearch
        verify(mockExamenSearchRepository, times(0)).save(examen);
    }

    @Test
    @Transactional
    public void checkDateExamenIsRequired() throws Exception {
        int databaseSizeBeforeTest = examenRepository.findAll().size();
        // set the field null
        examen.setDateExamen(null);

        // Create the Examen, which fails.
        ExamenDTO examenDTO = examenMapper.toDto(examen);

        restExamenMockMvc.perform(post("/api/examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examenDTO)))
            .andExpect(status().isBadRequest());

        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        // Get all the examenList
        restExamenMockMvc.perform(get("/api/examen?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examen.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateExamen").value(hasItem(DEFAULT_DATE_EXAMEN.toString())));
    }
    
    @Test
    @Transactional
    public void getExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        // Get the examen
        restExamenMockMvc.perform(get("/api/examen/{id}", examen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examen.getId().intValue()))
            .andExpect(jsonPath("$.dateExamen").value(DEFAULT_DATE_EXAMEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamen() throws Exception {
        // Get the examen
        restExamenMockMvc.perform(get("/api/examen/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        int databaseSizeBeforeUpdate = examenRepository.findAll().size();

        // Update the examen
        Examen updatedExamen = examenRepository.findById(examen.getId()).get();
        // Disconnect from session so that the updates on updatedExamen are not directly saved in db
        em.detach(updatedExamen);
        updatedExamen
            .dateExamen(UPDATED_DATE_EXAMEN);
        ExamenDTO examenDTO = examenMapper.toDto(updatedExamen);

        restExamenMockMvc.perform(put("/api/examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examenDTO)))
            .andExpect(status().isOk());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
        Examen testExamen = examenList.get(examenList.size() - 1);
        assertThat(testExamen.getDateExamen()).isEqualTo(UPDATED_DATE_EXAMEN);

        // Validate the Examen in Elasticsearch
        verify(mockExamenSearchRepository, times(1)).save(testExamen);
    }

    @Test
    @Transactional
    public void updateNonExistingExamen() throws Exception {
        int databaseSizeBeforeUpdate = examenRepository.findAll().size();

        // Create the Examen
        ExamenDTO examenDTO = examenMapper.toDto(examen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamenMockMvc.perform(put("/api/examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Examen in Elasticsearch
        verify(mockExamenSearchRepository, times(0)).save(examen);
    }

    @Test
    @Transactional
    public void deleteExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        int databaseSizeBeforeDelete = examenRepository.findAll().size();

        // Delete the examen
        restExamenMockMvc.perform(delete("/api/examen/{id}", examen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Examen in Elasticsearch
        verify(mockExamenSearchRepository, times(1)).deleteById(examen.getId());
    }

    @Test
    @Transactional
    public void searchExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);
        when(mockExamenSearchRepository.search(queryStringQuery("id:" + examen.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(examen), PageRequest.of(0, 1), 1));
        // Search the examen
        restExamenMockMvc.perform(get("/api/_search/examen?query=id:" + examen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examen.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateExamen").value(hasItem(DEFAULT_DATE_EXAMEN.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Examen.class);
        Examen examen1 = new Examen();
        examen1.setId(1L);
        Examen examen2 = new Examen();
        examen2.setId(examen1.getId());
        assertThat(examen1).isEqualTo(examen2);
        examen2.setId(2L);
        assertThat(examen1).isNotEqualTo(examen2);
        examen1.setId(null);
        assertThat(examen1).isNotEqualTo(examen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamenDTO.class);
        ExamenDTO examenDTO1 = new ExamenDTO();
        examenDTO1.setId(1L);
        ExamenDTO examenDTO2 = new ExamenDTO();
        assertThat(examenDTO1).isNotEqualTo(examenDTO2);
        examenDTO2.setId(examenDTO1.getId());
        assertThat(examenDTO1).isEqualTo(examenDTO2);
        examenDTO2.setId(2L);
        assertThat(examenDTO1).isNotEqualTo(examenDTO2);
        examenDTO1.setId(null);
        assertThat(examenDTO1).isNotEqualTo(examenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examenMapper.fromId(null)).isNull();
    }
}
