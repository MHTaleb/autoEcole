package dz.talcorp.ae.web.rest;

import dz.talcorp.ae.AutoEcoleApp;

import dz.talcorp.ae.domain.ExamenInfo;
import dz.talcorp.ae.domain.Examen;
import dz.talcorp.ae.domain.Candidat;
import dz.talcorp.ae.repository.ExamenInfoRepository;
import dz.talcorp.ae.repository.search.ExamenInfoSearchRepository;
import dz.talcorp.ae.service.ExamenInfoService;
import dz.talcorp.ae.service.dto.ExamenInfoDTO;
import dz.talcorp.ae.service.mapper.ExamenInfoMapper;
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

import dz.talcorp.ae.domain.enumeration.EtatExamen;
import dz.talcorp.ae.domain.enumeration.TypeExamen;
/**
 * Test class for the ExamenInfoResource REST controller.
 *
 * @see ExamenInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoEcoleApp.class)
public class ExamenInfoResourceIntTest {

    private static final EtatExamen DEFAULT_ETAT = EtatExamen.ENCOURS;
    private static final EtatExamen UPDATED_ETAT = EtatExamen.REUSSI;

    private static final TypeExamen DEFAULT_TYPE = TypeExamen.CODE;
    private static final TypeExamen UPDATED_TYPE = TypeExamen.CRENO;

    @Autowired
    private ExamenInfoRepository examenInfoRepository;

    @Autowired
    private ExamenInfoMapper examenInfoMapper;

    @Autowired
    private ExamenInfoService examenInfoService;

    /**
     * This repository is mocked in the dz.talcorp.ae.repository.search test package.
     *
     * @see dz.talcorp.ae.repository.search.ExamenInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ExamenInfoSearchRepository mockExamenInfoSearchRepository;

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

    private MockMvc restExamenInfoMockMvc;

    private ExamenInfo examenInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamenInfoResource examenInfoResource = new ExamenInfoResource(examenInfoService);
        this.restExamenInfoMockMvc = MockMvcBuilders.standaloneSetup(examenInfoResource)
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
    public static ExamenInfo createEntity(EntityManager em) {
        ExamenInfo examenInfo = new ExamenInfo()
            .etat(DEFAULT_ETAT)
            .type(DEFAULT_TYPE);
        // Add required entity
        Examen examen = ExamenResourceIntTest.createEntity(em);
        em.persist(examen);
        em.flush();
        examenInfo.setExamen(examen);
        // Add required entity
        Candidat candidat = CandidatResourceIntTest.createEntity(em);
        em.persist(candidat);
        em.flush();
        examenInfo.setCandidat(candidat);
        return examenInfo;
    }

    @Before
    public void initTest() {
        examenInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamenInfo() throws Exception {
        int databaseSizeBeforeCreate = examenInfoRepository.findAll().size();

        // Create the ExamenInfo
        ExamenInfoDTO examenInfoDTO = examenInfoMapper.toDto(examenInfo);
        restExamenInfoMockMvc.perform(post("/api/examen-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examenInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamenInfo in the database
        List<ExamenInfo> examenInfoList = examenInfoRepository.findAll();
        assertThat(examenInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ExamenInfo testExamenInfo = examenInfoList.get(examenInfoList.size() - 1);
        assertThat(testExamenInfo.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testExamenInfo.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the ExamenInfo in Elasticsearch
        verify(mockExamenInfoSearchRepository, times(1)).save(testExamenInfo);
    }

    @Test
    @Transactional
    public void createExamenInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examenInfoRepository.findAll().size();

        // Create the ExamenInfo with an existing ID
        examenInfo.setId(1L);
        ExamenInfoDTO examenInfoDTO = examenInfoMapper.toDto(examenInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamenInfoMockMvc.perform(post("/api/examen-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examenInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamenInfo in the database
        List<ExamenInfo> examenInfoList = examenInfoRepository.findAll();
        assertThat(examenInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ExamenInfo in Elasticsearch
        verify(mockExamenInfoSearchRepository, times(0)).save(examenInfo);
    }

    @Test
    @Transactional
    public void getAllExamenInfos() throws Exception {
        // Initialize the database
        examenInfoRepository.saveAndFlush(examenInfo);

        // Get all the examenInfoList
        restExamenInfoMockMvc.perform(get("/api/examen-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examenInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getExamenInfo() throws Exception {
        // Initialize the database
        examenInfoRepository.saveAndFlush(examenInfo);

        // Get the examenInfo
        restExamenInfoMockMvc.perform(get("/api/examen-infos/{id}", examenInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examenInfo.getId().intValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamenInfo() throws Exception {
        // Get the examenInfo
        restExamenInfoMockMvc.perform(get("/api/examen-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamenInfo() throws Exception {
        // Initialize the database
        examenInfoRepository.saveAndFlush(examenInfo);

        int databaseSizeBeforeUpdate = examenInfoRepository.findAll().size();

        // Update the examenInfo
        ExamenInfo updatedExamenInfo = examenInfoRepository.findById(examenInfo.getId()).get();
        // Disconnect from session so that the updates on updatedExamenInfo are not directly saved in db
        em.detach(updatedExamenInfo);
        updatedExamenInfo
            .etat(UPDATED_ETAT)
            .type(UPDATED_TYPE);
        ExamenInfoDTO examenInfoDTO = examenInfoMapper.toDto(updatedExamenInfo);

        restExamenInfoMockMvc.perform(put("/api/examen-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examenInfoDTO)))
            .andExpect(status().isOk());

        // Validate the ExamenInfo in the database
        List<ExamenInfo> examenInfoList = examenInfoRepository.findAll();
        assertThat(examenInfoList).hasSize(databaseSizeBeforeUpdate);
        ExamenInfo testExamenInfo = examenInfoList.get(examenInfoList.size() - 1);
        assertThat(testExamenInfo.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testExamenInfo.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the ExamenInfo in Elasticsearch
        verify(mockExamenInfoSearchRepository, times(1)).save(testExamenInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingExamenInfo() throws Exception {
        int databaseSizeBeforeUpdate = examenInfoRepository.findAll().size();

        // Create the ExamenInfo
        ExamenInfoDTO examenInfoDTO = examenInfoMapper.toDto(examenInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamenInfoMockMvc.perform(put("/api/examen-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examenInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamenInfo in the database
        List<ExamenInfo> examenInfoList = examenInfoRepository.findAll();
        assertThat(examenInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ExamenInfo in Elasticsearch
        verify(mockExamenInfoSearchRepository, times(0)).save(examenInfo);
    }

    @Test
    @Transactional
    public void deleteExamenInfo() throws Exception {
        // Initialize the database
        examenInfoRepository.saveAndFlush(examenInfo);

        int databaseSizeBeforeDelete = examenInfoRepository.findAll().size();

        // Delete the examenInfo
        restExamenInfoMockMvc.perform(delete("/api/examen-infos/{id}", examenInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExamenInfo> examenInfoList = examenInfoRepository.findAll();
        assertThat(examenInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ExamenInfo in Elasticsearch
        verify(mockExamenInfoSearchRepository, times(1)).deleteById(examenInfo.getId());
    }

    @Test
    @Transactional
    public void searchExamenInfo() throws Exception {
        // Initialize the database
        examenInfoRepository.saveAndFlush(examenInfo);
        when(mockExamenInfoSearchRepository.search(queryStringQuery("id:" + examenInfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(examenInfo), PageRequest.of(0, 1), 1));
        // Search the examenInfo
        restExamenInfoMockMvc.perform(get("/api/_search/examen-infos?query=id:" + examenInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examenInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamenInfo.class);
        ExamenInfo examenInfo1 = new ExamenInfo();
        examenInfo1.setId(1L);
        ExamenInfo examenInfo2 = new ExamenInfo();
        examenInfo2.setId(examenInfo1.getId());
        assertThat(examenInfo1).isEqualTo(examenInfo2);
        examenInfo2.setId(2L);
        assertThat(examenInfo1).isNotEqualTo(examenInfo2);
        examenInfo1.setId(null);
        assertThat(examenInfo1).isNotEqualTo(examenInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamenInfoDTO.class);
        ExamenInfoDTO examenInfoDTO1 = new ExamenInfoDTO();
        examenInfoDTO1.setId(1L);
        ExamenInfoDTO examenInfoDTO2 = new ExamenInfoDTO();
        assertThat(examenInfoDTO1).isNotEqualTo(examenInfoDTO2);
        examenInfoDTO2.setId(examenInfoDTO1.getId());
        assertThat(examenInfoDTO1).isEqualTo(examenInfoDTO2);
        examenInfoDTO2.setId(2L);
        assertThat(examenInfoDTO1).isNotEqualTo(examenInfoDTO2);
        examenInfoDTO1.setId(null);
        assertThat(examenInfoDTO1).isNotEqualTo(examenInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examenInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examenInfoMapper.fromId(null)).isNull();
    }
}
