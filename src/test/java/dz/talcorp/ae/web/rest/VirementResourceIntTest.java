package dz.talcorp.ae.web.rest;

import dz.talcorp.ae.AutoEcoleApp;

import dz.talcorp.ae.domain.Virement;
import dz.talcorp.ae.domain.Candidat;
import dz.talcorp.ae.repository.VirementRepository;
import dz.talcorp.ae.repository.search.VirementSearchRepository;
import dz.talcorp.ae.service.VirementService;
import dz.talcorp.ae.service.dto.VirementDTO;
import dz.talcorp.ae.service.mapper.VirementMapper;
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
 * Test class for the VirementResource REST controller.
 *
 * @see VirementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoEcoleApp.class)
public class VirementResourceIntTest {

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final LocalDate DEFAULT_DATE_VIREMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VIREMENT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private VirementRepository virementRepository;

    @Autowired
    private VirementMapper virementMapper;

    @Autowired
    private VirementService virementService;

    /**
     * This repository is mocked in the dz.talcorp.ae.repository.search test package.
     *
     * @see dz.talcorp.ae.repository.search.VirementSearchRepositoryMockConfiguration
     */
    @Autowired
    private VirementSearchRepository mockVirementSearchRepository;

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

    private MockMvc restVirementMockMvc;

    private Virement virement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VirementResource virementResource = new VirementResource(virementService);
        this.restVirementMockMvc = MockMvcBuilders.standaloneSetup(virementResource)
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
    public static Virement createEntity(EntityManager em) {
        Virement virement = new Virement()
            .montant(DEFAULT_MONTANT)
            .dateVirement(DEFAULT_DATE_VIREMENT);
        // Add required entity
        Candidat candidat = CandidatResourceIntTest.createEntity(em);
        em.persist(candidat);
        em.flush();
        virement.setCandidat(candidat);
        return virement;
    }

    @Before
    public void initTest() {
        virement = createEntity(em);
    }

    @Test
    @Transactional
    public void createVirement() throws Exception {
        int databaseSizeBeforeCreate = virementRepository.findAll().size();

        // Create the Virement
        VirementDTO virementDTO = virementMapper.toDto(virement);
        restVirementMockMvc.perform(post("/api/virements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virementDTO)))
            .andExpect(status().isCreated());

        // Validate the Virement in the database
        List<Virement> virementList = virementRepository.findAll();
        assertThat(virementList).hasSize(databaseSizeBeforeCreate + 1);
        Virement testVirement = virementList.get(virementList.size() - 1);
        assertThat(testVirement.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testVirement.getDateVirement()).isEqualTo(DEFAULT_DATE_VIREMENT);

        // Validate the Virement in Elasticsearch
        verify(mockVirementSearchRepository, times(1)).save(testVirement);
    }

    @Test
    @Transactional
    public void createVirementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = virementRepository.findAll().size();

        // Create the Virement with an existing ID
        virement.setId(1L);
        VirementDTO virementDTO = virementMapper.toDto(virement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVirementMockMvc.perform(post("/api/virements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Virement in the database
        List<Virement> virementList = virementRepository.findAll();
        assertThat(virementList).hasSize(databaseSizeBeforeCreate);

        // Validate the Virement in Elasticsearch
        verify(mockVirementSearchRepository, times(0)).save(virement);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = virementRepository.findAll().size();
        // set the field null
        virement.setMontant(null);

        // Create the Virement, which fails.
        VirementDTO virementDTO = virementMapper.toDto(virement);

        restVirementMockMvc.perform(post("/api/virements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virementDTO)))
            .andExpect(status().isBadRequest());

        List<Virement> virementList = virementRepository.findAll();
        assertThat(virementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateVirementIsRequired() throws Exception {
        int databaseSizeBeforeTest = virementRepository.findAll().size();
        // set the field null
        virement.setDateVirement(null);

        // Create the Virement, which fails.
        VirementDTO virementDTO = virementMapper.toDto(virement);

        restVirementMockMvc.perform(post("/api/virements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virementDTO)))
            .andExpect(status().isBadRequest());

        List<Virement> virementList = virementRepository.findAll();
        assertThat(virementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVirements() throws Exception {
        // Initialize the database
        virementRepository.saveAndFlush(virement);

        // Get all the virementList
        restVirementMockMvc.perform(get("/api/virements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(virement.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateVirement").value(hasItem(DEFAULT_DATE_VIREMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getVirement() throws Exception {
        // Initialize the database
        virementRepository.saveAndFlush(virement);

        // Get the virement
        restVirementMockMvc.perform(get("/api/virements/{id}", virement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(virement.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.dateVirement").value(DEFAULT_DATE_VIREMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVirement() throws Exception {
        // Get the virement
        restVirementMockMvc.perform(get("/api/virements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVirement() throws Exception {
        // Initialize the database
        virementRepository.saveAndFlush(virement);

        int databaseSizeBeforeUpdate = virementRepository.findAll().size();

        // Update the virement
        Virement updatedVirement = virementRepository.findById(virement.getId()).get();
        // Disconnect from session so that the updates on updatedVirement are not directly saved in db
        em.detach(updatedVirement);
        updatedVirement
            .montant(UPDATED_MONTANT)
            .dateVirement(UPDATED_DATE_VIREMENT);
        VirementDTO virementDTO = virementMapper.toDto(updatedVirement);

        restVirementMockMvc.perform(put("/api/virements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virementDTO)))
            .andExpect(status().isOk());

        // Validate the Virement in the database
        List<Virement> virementList = virementRepository.findAll();
        assertThat(virementList).hasSize(databaseSizeBeforeUpdate);
        Virement testVirement = virementList.get(virementList.size() - 1);
        assertThat(testVirement.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testVirement.getDateVirement()).isEqualTo(UPDATED_DATE_VIREMENT);

        // Validate the Virement in Elasticsearch
        verify(mockVirementSearchRepository, times(1)).save(testVirement);
    }

    @Test
    @Transactional
    public void updateNonExistingVirement() throws Exception {
        int databaseSizeBeforeUpdate = virementRepository.findAll().size();

        // Create the Virement
        VirementDTO virementDTO = virementMapper.toDto(virement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVirementMockMvc.perform(put("/api/virements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Virement in the database
        List<Virement> virementList = virementRepository.findAll();
        assertThat(virementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Virement in Elasticsearch
        verify(mockVirementSearchRepository, times(0)).save(virement);
    }

    @Test
    @Transactional
    public void deleteVirement() throws Exception {
        // Initialize the database
        virementRepository.saveAndFlush(virement);

        int databaseSizeBeforeDelete = virementRepository.findAll().size();

        // Delete the virement
        restVirementMockMvc.perform(delete("/api/virements/{id}", virement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Virement> virementList = virementRepository.findAll();
        assertThat(virementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Virement in Elasticsearch
        verify(mockVirementSearchRepository, times(1)).deleteById(virement.getId());
    }

    @Test
    @Transactional
    public void searchVirement() throws Exception {
        // Initialize the database
        virementRepository.saveAndFlush(virement);
        when(mockVirementSearchRepository.search(queryStringQuery("id:" + virement.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(virement), PageRequest.of(0, 1), 1));
        // Search the virement
        restVirementMockMvc.perform(get("/api/_search/virements?query=id:" + virement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(virement.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateVirement").value(hasItem(DEFAULT_DATE_VIREMENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Virement.class);
        Virement virement1 = new Virement();
        virement1.setId(1L);
        Virement virement2 = new Virement();
        virement2.setId(virement1.getId());
        assertThat(virement1).isEqualTo(virement2);
        virement2.setId(2L);
        assertThat(virement1).isNotEqualTo(virement2);
        virement1.setId(null);
        assertThat(virement1).isNotEqualTo(virement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VirementDTO.class);
        VirementDTO virementDTO1 = new VirementDTO();
        virementDTO1.setId(1L);
        VirementDTO virementDTO2 = new VirementDTO();
        assertThat(virementDTO1).isNotEqualTo(virementDTO2);
        virementDTO2.setId(virementDTO1.getId());
        assertThat(virementDTO1).isEqualTo(virementDTO2);
        virementDTO2.setId(2L);
        assertThat(virementDTO1).isNotEqualTo(virementDTO2);
        virementDTO1.setId(null);
        assertThat(virementDTO1).isNotEqualTo(virementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(virementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(virementMapper.fromId(null)).isNull();
    }
}
