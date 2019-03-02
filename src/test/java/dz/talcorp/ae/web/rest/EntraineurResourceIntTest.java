package dz.talcorp.ae.web.rest;

import dz.talcorp.ae.AutoEcoleApp;

import dz.talcorp.ae.domain.Entraineur;
import dz.talcorp.ae.repository.EntraineurRepository;
import dz.talcorp.ae.repository.search.EntraineurSearchRepository;
import dz.talcorp.ae.service.EntraineurService;
import dz.talcorp.ae.service.dto.EntraineurDTO;
import dz.talcorp.ae.service.mapper.EntraineurMapper;
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
import org.springframework.util.Base64Utils;
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
 * Test class for the EntraineurResource REST controller.
 *
 * @see EntraineurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoEcoleApp.class)
public class EntraineurResourceIntTest {

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "544156533344004";
    private static final String UPDATED_TELEPHONE = "883158646638228";

    @Autowired
    private EntraineurRepository entraineurRepository;

    @Autowired
    private EntraineurMapper entraineurMapper;

    @Autowired
    private EntraineurService entraineurService;

    /**
     * This repository is mocked in the dz.talcorp.ae.repository.search test package.
     *
     * @see dz.talcorp.ae.repository.search.EntraineurSearchRepositoryMockConfiguration
     */
    @Autowired
    private EntraineurSearchRepository mockEntraineurSearchRepository;

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

    private MockMvc restEntraineurMockMvc;

    private Entraineur entraineur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntraineurResource entraineurResource = new EntraineurResource(entraineurService);
        this.restEntraineurMockMvc = MockMvcBuilders.standaloneSetup(entraineurResource)
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
    public static Entraineur createEntity(EntityManager em) {
        Entraineur entraineur = new Entraineur()
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE);
        return entraineur;
    }

    @Before
    public void initTest() {
        entraineur = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntraineur() throws Exception {
        int databaseSizeBeforeCreate = entraineurRepository.findAll().size();

        // Create the Entraineur
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);
        restEntraineurMockMvc.perform(post("/api/entraineurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entraineurDTO)))
            .andExpect(status().isCreated());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeCreate + 1);
        Entraineur testEntraineur = entraineurList.get(entraineurList.size() - 1);
        assertThat(testEntraineur.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testEntraineur.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testEntraineur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEntraineur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testEntraineur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);

        // Validate the Entraineur in Elasticsearch
        verify(mockEntraineurSearchRepository, times(1)).save(testEntraineur);
    }

    @Test
    @Transactional
    public void createEntraineurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entraineurRepository.findAll().size();

        // Create the Entraineur with an existing ID
        entraineur.setId(1L);
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntraineurMockMvc.perform(post("/api/entraineurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entraineurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeCreate);

        // Validate the Entraineur in Elasticsearch
        verify(mockEntraineurSearchRepository, times(0)).save(entraineur);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = entraineurRepository.findAll().size();
        // set the field null
        entraineur.setNom(null);

        // Create the Entraineur, which fails.
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);

        restEntraineurMockMvc.perform(post("/api/entraineurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entraineurDTO)))
            .andExpect(status().isBadRequest());

        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = entraineurRepository.findAll().size();
        // set the field null
        entraineur.setPrenom(null);

        // Create the Entraineur, which fails.
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);

        restEntraineurMockMvc.perform(post("/api/entraineurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entraineurDTO)))
            .andExpect(status().isBadRequest());

        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = entraineurRepository.findAll().size();
        // set the field null
        entraineur.setTelephone(null);

        // Create the Entraineur, which fails.
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);

        restEntraineurMockMvc.perform(post("/api/entraineurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entraineurDTO)))
            .andExpect(status().isBadRequest());

        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntraineurs() throws Exception {
        // Initialize the database
        entraineurRepository.saveAndFlush(entraineur);

        // Get all the entraineurList
        restEntraineurMockMvc.perform(get("/api/entraineurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entraineur.getId().intValue())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())));
    }
    
    @Test
    @Transactional
    public void getEntraineur() throws Exception {
        // Initialize the database
        entraineurRepository.saveAndFlush(entraineur);

        // Get the entraineur
        restEntraineurMockMvc.perform(get("/api/entraineurs/{id}", entraineur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entraineur.getId().intValue()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntraineur() throws Exception {
        // Get the entraineur
        restEntraineurMockMvc.perform(get("/api/entraineurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntraineur() throws Exception {
        // Initialize the database
        entraineurRepository.saveAndFlush(entraineur);

        int databaseSizeBeforeUpdate = entraineurRepository.findAll().size();

        // Update the entraineur
        Entraineur updatedEntraineur = entraineurRepository.findById(entraineur.getId()).get();
        // Disconnect from session so that the updates on updatedEntraineur are not directly saved in db
        em.detach(updatedEntraineur);
        updatedEntraineur
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE);
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(updatedEntraineur);

        restEntraineurMockMvc.perform(put("/api/entraineurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entraineurDTO)))
            .andExpect(status().isOk());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeUpdate);
        Entraineur testEntraineur = entraineurList.get(entraineurList.size() - 1);
        assertThat(testEntraineur.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testEntraineur.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testEntraineur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEntraineur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEntraineur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);

        // Validate the Entraineur in Elasticsearch
        verify(mockEntraineurSearchRepository, times(1)).save(testEntraineur);
    }

    @Test
    @Transactional
    public void updateNonExistingEntraineur() throws Exception {
        int databaseSizeBeforeUpdate = entraineurRepository.findAll().size();

        // Create the Entraineur
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntraineurMockMvc.perform(put("/api/entraineurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entraineurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Entraineur in Elasticsearch
        verify(mockEntraineurSearchRepository, times(0)).save(entraineur);
    }

    @Test
    @Transactional
    public void deleteEntraineur() throws Exception {
        // Initialize the database
        entraineurRepository.saveAndFlush(entraineur);

        int databaseSizeBeforeDelete = entraineurRepository.findAll().size();

        // Delete the entraineur
        restEntraineurMockMvc.perform(delete("/api/entraineurs/{id}", entraineur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Entraineur in Elasticsearch
        verify(mockEntraineurSearchRepository, times(1)).deleteById(entraineur.getId());
    }

    @Test
    @Transactional
    public void searchEntraineur() throws Exception {
        // Initialize the database
        entraineurRepository.saveAndFlush(entraineur);
        when(mockEntraineurSearchRepository.search(queryStringQuery("id:" + entraineur.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(entraineur), PageRequest.of(0, 1), 1));
        // Search the entraineur
        restEntraineurMockMvc.perform(get("/api/_search/entraineurs?query=id:" + entraineur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entraineur.getId().intValue())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entraineur.class);
        Entraineur entraineur1 = new Entraineur();
        entraineur1.setId(1L);
        Entraineur entraineur2 = new Entraineur();
        entraineur2.setId(entraineur1.getId());
        assertThat(entraineur1).isEqualTo(entraineur2);
        entraineur2.setId(2L);
        assertThat(entraineur1).isNotEqualTo(entraineur2);
        entraineur1.setId(null);
        assertThat(entraineur1).isNotEqualTo(entraineur2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntraineurDTO.class);
        EntraineurDTO entraineurDTO1 = new EntraineurDTO();
        entraineurDTO1.setId(1L);
        EntraineurDTO entraineurDTO2 = new EntraineurDTO();
        assertThat(entraineurDTO1).isNotEqualTo(entraineurDTO2);
        entraineurDTO2.setId(entraineurDTO1.getId());
        assertThat(entraineurDTO1).isEqualTo(entraineurDTO2);
        entraineurDTO2.setId(2L);
        assertThat(entraineurDTO1).isNotEqualTo(entraineurDTO2);
        entraineurDTO1.setId(null);
        assertThat(entraineurDTO1).isNotEqualTo(entraineurDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(entraineurMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(entraineurMapper.fromId(null)).isNull();
    }
}
