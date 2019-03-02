package dz.talcorp.ae.web.rest;

import dz.talcorp.ae.AutoEcoleApp;

import dz.talcorp.ae.domain.Ecole;
import dz.talcorp.ae.repository.EcoleRepository;
import dz.talcorp.ae.repository.search.EcoleSearchRepository;
import dz.talcorp.ae.service.EcoleService;
import dz.talcorp.ae.service.dto.EcoleDTO;
import dz.talcorp.ae.service.mapper.EcoleMapper;
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
 * Test class for the EcoleResource REST controller.
 *
 * @see EcoleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoEcoleApp.class)
public class EcoleResourceIntTest {

    private static final String DEFAULT_NOM_ECOLE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ECOLE = "BBBBBBBBBB";

    private static final String DEFAULT_PRESIDENT = "AAAAAAAAAA";
    private static final String UPDATED_PRESIDENT = "BBBBBBBBBB";

    @Autowired
    private EcoleRepository ecoleRepository;

    @Autowired
    private EcoleMapper ecoleMapper;

    @Autowired
    private EcoleService ecoleService;

    /**
     * This repository is mocked in the dz.talcorp.ae.repository.search test package.
     *
     * @see dz.talcorp.ae.repository.search.EcoleSearchRepositoryMockConfiguration
     */
    @Autowired
    private EcoleSearchRepository mockEcoleSearchRepository;

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

    private MockMvc restEcoleMockMvc;

    private Ecole ecole;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EcoleResource ecoleResource = new EcoleResource(ecoleService);
        this.restEcoleMockMvc = MockMvcBuilders.standaloneSetup(ecoleResource)
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
    public static Ecole createEntity(EntityManager em) {
        Ecole ecole = new Ecole()
            .nomEcole(DEFAULT_NOM_ECOLE)
            .president(DEFAULT_PRESIDENT);
        return ecole;
    }

    @Before
    public void initTest() {
        ecole = createEntity(em);
    }

    @Test
    @Transactional
    public void createEcole() throws Exception {
        int databaseSizeBeforeCreate = ecoleRepository.findAll().size();

        // Create the Ecole
        EcoleDTO ecoleDTO = ecoleMapper.toDto(ecole);
        restEcoleMockMvc.perform(post("/api/ecoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ecoleDTO)))
            .andExpect(status().isCreated());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeCreate + 1);
        Ecole testEcole = ecoleList.get(ecoleList.size() - 1);
        assertThat(testEcole.getNomEcole()).isEqualTo(DEFAULT_NOM_ECOLE);
        assertThat(testEcole.getPresident()).isEqualTo(DEFAULT_PRESIDENT);

        // Validate the Ecole in Elasticsearch
        verify(mockEcoleSearchRepository, times(1)).save(testEcole);
    }

    @Test
    @Transactional
    public void createEcoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ecoleRepository.findAll().size();

        // Create the Ecole with an existing ID
        ecole.setId(1L);
        EcoleDTO ecoleDTO = ecoleMapper.toDto(ecole);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcoleMockMvc.perform(post("/api/ecoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ecoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeCreate);

        // Validate the Ecole in Elasticsearch
        verify(mockEcoleSearchRepository, times(0)).save(ecole);
    }

    @Test
    @Transactional
    public void checkNomEcoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = ecoleRepository.findAll().size();
        // set the field null
        ecole.setNomEcole(null);

        // Create the Ecole, which fails.
        EcoleDTO ecoleDTO = ecoleMapper.toDto(ecole);

        restEcoleMockMvc.perform(post("/api/ecoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ecoleDTO)))
            .andExpect(status().isBadRequest());

        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPresidentIsRequired() throws Exception {
        int databaseSizeBeforeTest = ecoleRepository.findAll().size();
        // set the field null
        ecole.setPresident(null);

        // Create the Ecole, which fails.
        EcoleDTO ecoleDTO = ecoleMapper.toDto(ecole);

        restEcoleMockMvc.perform(post("/api/ecoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ecoleDTO)))
            .andExpect(status().isBadRequest());

        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEcoles() throws Exception {
        // Initialize the database
        ecoleRepository.saveAndFlush(ecole);

        // Get all the ecoleList
        restEcoleMockMvc.perform(get("/api/ecoles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecole.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomEcole").value(hasItem(DEFAULT_NOM_ECOLE.toString())))
            .andExpect(jsonPath("$.[*].president").value(hasItem(DEFAULT_PRESIDENT.toString())));
    }
    
    @Test
    @Transactional
    public void getEcole() throws Exception {
        // Initialize the database
        ecoleRepository.saveAndFlush(ecole);

        // Get the ecole
        restEcoleMockMvc.perform(get("/api/ecoles/{id}", ecole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ecole.getId().intValue()))
            .andExpect(jsonPath("$.nomEcole").value(DEFAULT_NOM_ECOLE.toString()))
            .andExpect(jsonPath("$.president").value(DEFAULT_PRESIDENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEcole() throws Exception {
        // Get the ecole
        restEcoleMockMvc.perform(get("/api/ecoles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEcole() throws Exception {
        // Initialize the database
        ecoleRepository.saveAndFlush(ecole);

        int databaseSizeBeforeUpdate = ecoleRepository.findAll().size();

        // Update the ecole
        Ecole updatedEcole = ecoleRepository.findById(ecole.getId()).get();
        // Disconnect from session so that the updates on updatedEcole are not directly saved in db
        em.detach(updatedEcole);
        updatedEcole
            .nomEcole(UPDATED_NOM_ECOLE)
            .president(UPDATED_PRESIDENT);
        EcoleDTO ecoleDTO = ecoleMapper.toDto(updatedEcole);

        restEcoleMockMvc.perform(put("/api/ecoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ecoleDTO)))
            .andExpect(status().isOk());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeUpdate);
        Ecole testEcole = ecoleList.get(ecoleList.size() - 1);
        assertThat(testEcole.getNomEcole()).isEqualTo(UPDATED_NOM_ECOLE);
        assertThat(testEcole.getPresident()).isEqualTo(UPDATED_PRESIDENT);

        // Validate the Ecole in Elasticsearch
        verify(mockEcoleSearchRepository, times(1)).save(testEcole);
    }

    @Test
    @Transactional
    public void updateNonExistingEcole() throws Exception {
        int databaseSizeBeforeUpdate = ecoleRepository.findAll().size();

        // Create the Ecole
        EcoleDTO ecoleDTO = ecoleMapper.toDto(ecole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcoleMockMvc.perform(put("/api/ecoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ecoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ecole in Elasticsearch
        verify(mockEcoleSearchRepository, times(0)).save(ecole);
    }

    @Test
    @Transactional
    public void deleteEcole() throws Exception {
        // Initialize the database
        ecoleRepository.saveAndFlush(ecole);

        int databaseSizeBeforeDelete = ecoleRepository.findAll().size();

        // Delete the ecole
        restEcoleMockMvc.perform(delete("/api/ecoles/{id}", ecole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Ecole in Elasticsearch
        verify(mockEcoleSearchRepository, times(1)).deleteById(ecole.getId());
    }

    @Test
    @Transactional
    public void searchEcole() throws Exception {
        // Initialize the database
        ecoleRepository.saveAndFlush(ecole);
        when(mockEcoleSearchRepository.search(queryStringQuery("id:" + ecole.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ecole), PageRequest.of(0, 1), 1));
        // Search the ecole
        restEcoleMockMvc.perform(get("/api/_search/ecoles?query=id:" + ecole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecole.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomEcole").value(hasItem(DEFAULT_NOM_ECOLE)))
            .andExpect(jsonPath("$.[*].president").value(hasItem(DEFAULT_PRESIDENT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ecole.class);
        Ecole ecole1 = new Ecole();
        ecole1.setId(1L);
        Ecole ecole2 = new Ecole();
        ecole2.setId(ecole1.getId());
        assertThat(ecole1).isEqualTo(ecole2);
        ecole2.setId(2L);
        assertThat(ecole1).isNotEqualTo(ecole2);
        ecole1.setId(null);
        assertThat(ecole1).isNotEqualTo(ecole2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EcoleDTO.class);
        EcoleDTO ecoleDTO1 = new EcoleDTO();
        ecoleDTO1.setId(1L);
        EcoleDTO ecoleDTO2 = new EcoleDTO();
        assertThat(ecoleDTO1).isNotEqualTo(ecoleDTO2);
        ecoleDTO2.setId(ecoleDTO1.getId());
        assertThat(ecoleDTO1).isEqualTo(ecoleDTO2);
        ecoleDTO2.setId(2L);
        assertThat(ecoleDTO1).isNotEqualTo(ecoleDTO2);
        ecoleDTO1.setId(null);
        assertThat(ecoleDTO1).isNotEqualTo(ecoleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ecoleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ecoleMapper.fromId(null)).isNull();
    }
}
