package dz.talcorp.ae.web.rest;

import dz.talcorp.ae.AutoEcoleV01App;

import dz.talcorp.ae.domain.Examinateur;
import dz.talcorp.ae.repository.ExaminateurRepository;
import dz.talcorp.ae.service.ExaminateurService;
import dz.talcorp.ae.service.dto.ExaminateurDTO;
import dz.talcorp.ae.service.mapper.ExaminateurMapper;
import dz.talcorp.ae.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static dz.talcorp.ae.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExaminateurResource REST controller.
 *
 * @see ExaminateurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoEcoleV01App.class)
public class ExaminateurResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "29246433676439";
    private static final String UPDATED_TELEPHONE = "624570766237293";

    @Autowired
    private ExaminateurRepository examinateurRepository;

    @Autowired
    private ExaminateurMapper examinateurMapper;

    @Autowired
    private ExaminateurService examinateurService;

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

    private MockMvc restExaminateurMockMvc;

    private Examinateur examinateur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExaminateurResource examinateurResource = new ExaminateurResource(examinateurService);
        this.restExaminateurMockMvc = MockMvcBuilders.standaloneSetup(examinateurResource)
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
    public static Examinateur createEntity(EntityManager em) {
        Examinateur examinateur = new Examinateur()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE);
        return examinateur;
    }

    @Before
    public void initTest() {
        examinateur = createEntity(em);
    }

    @Test
    @Transactional
    public void createExaminateur() throws Exception {
        int databaseSizeBeforeCreate = examinateurRepository.findAll().size();

        // Create the Examinateur
        ExaminateurDTO examinateurDTO = examinateurMapper.toDto(examinateur);
        restExaminateurMockMvc.perform(post("/api/examinateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinateurDTO)))
            .andExpect(status().isCreated());

        // Validate the Examinateur in the database
        List<Examinateur> examinateurList = examinateurRepository.findAll();
        assertThat(examinateurList).hasSize(databaseSizeBeforeCreate + 1);
        Examinateur testExaminateur = examinateurList.get(examinateurList.size() - 1);
        assertThat(testExaminateur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testExaminateur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testExaminateur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    @Transactional
    public void createExaminateurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examinateurRepository.findAll().size();

        // Create the Examinateur with an existing ID
        examinateur.setId(1L);
        ExaminateurDTO examinateurDTO = examinateurMapper.toDto(examinateur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExaminateurMockMvc.perform(post("/api/examinateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinateurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Examinateur in the database
        List<Examinateur> examinateurList = examinateurRepository.findAll();
        assertThat(examinateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = examinateurRepository.findAll().size();
        // set the field null
        examinateur.setNom(null);

        // Create the Examinateur, which fails.
        ExaminateurDTO examinateurDTO = examinateurMapper.toDto(examinateur);

        restExaminateurMockMvc.perform(post("/api/examinateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinateurDTO)))
            .andExpect(status().isBadRequest());

        List<Examinateur> examinateurList = examinateurRepository.findAll();
        assertThat(examinateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = examinateurRepository.findAll().size();
        // set the field null
        examinateur.setPrenom(null);

        // Create the Examinateur, which fails.
        ExaminateurDTO examinateurDTO = examinateurMapper.toDto(examinateur);

        restExaminateurMockMvc.perform(post("/api/examinateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinateurDTO)))
            .andExpect(status().isBadRequest());

        List<Examinateur> examinateurList = examinateurRepository.findAll();
        assertThat(examinateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExaminateurs() throws Exception {
        // Initialize the database
        examinateurRepository.saveAndFlush(examinateur);

        // Get all the examinateurList
        restExaminateurMockMvc.perform(get("/api/examinateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examinateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())));
    }
    
    @Test
    @Transactional
    public void getExaminateur() throws Exception {
        // Initialize the database
        examinateurRepository.saveAndFlush(examinateur);

        // Get the examinateur
        restExaminateurMockMvc.perform(get("/api/examinateurs/{id}", examinateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examinateur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExaminateur() throws Exception {
        // Get the examinateur
        restExaminateurMockMvc.perform(get("/api/examinateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExaminateur() throws Exception {
        // Initialize the database
        examinateurRepository.saveAndFlush(examinateur);

        int databaseSizeBeforeUpdate = examinateurRepository.findAll().size();

        // Update the examinateur
        Examinateur updatedExaminateur = examinateurRepository.findById(examinateur.getId()).get();
        // Disconnect from session so that the updates on updatedExaminateur are not directly saved in db
        em.detach(updatedExaminateur);
        updatedExaminateur
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE);
        ExaminateurDTO examinateurDTO = examinateurMapper.toDto(updatedExaminateur);

        restExaminateurMockMvc.perform(put("/api/examinateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinateurDTO)))
            .andExpect(status().isOk());

        // Validate the Examinateur in the database
        List<Examinateur> examinateurList = examinateurRepository.findAll();
        assertThat(examinateurList).hasSize(databaseSizeBeforeUpdate);
        Examinateur testExaminateur = examinateurList.get(examinateurList.size() - 1);
        assertThat(testExaminateur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testExaminateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testExaminateur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingExaminateur() throws Exception {
        int databaseSizeBeforeUpdate = examinateurRepository.findAll().size();

        // Create the Examinateur
        ExaminateurDTO examinateurDTO = examinateurMapper.toDto(examinateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExaminateurMockMvc.perform(put("/api/examinateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examinateurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Examinateur in the database
        List<Examinateur> examinateurList = examinateurRepository.findAll();
        assertThat(examinateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExaminateur() throws Exception {
        // Initialize the database
        examinateurRepository.saveAndFlush(examinateur);

        int databaseSizeBeforeDelete = examinateurRepository.findAll().size();

        // Delete the examinateur
        restExaminateurMockMvc.perform(delete("/api/examinateurs/{id}", examinateur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Examinateur> examinateurList = examinateurRepository.findAll();
        assertThat(examinateurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Examinateur.class);
        Examinateur examinateur1 = new Examinateur();
        examinateur1.setId(1L);
        Examinateur examinateur2 = new Examinateur();
        examinateur2.setId(examinateur1.getId());
        assertThat(examinateur1).isEqualTo(examinateur2);
        examinateur2.setId(2L);
        assertThat(examinateur1).isNotEqualTo(examinateur2);
        examinateur1.setId(null);
        assertThat(examinateur1).isNotEqualTo(examinateur2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExaminateurDTO.class);
        ExaminateurDTO examinateurDTO1 = new ExaminateurDTO();
        examinateurDTO1.setId(1L);
        ExaminateurDTO examinateurDTO2 = new ExaminateurDTO();
        assertThat(examinateurDTO1).isNotEqualTo(examinateurDTO2);
        examinateurDTO2.setId(examinateurDTO1.getId());
        assertThat(examinateurDTO1).isEqualTo(examinateurDTO2);
        examinateurDTO2.setId(2L);
        assertThat(examinateurDTO1).isNotEqualTo(examinateurDTO2);
        examinateurDTO1.setId(null);
        assertThat(examinateurDTO1).isNotEqualTo(examinateurDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examinateurMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examinateurMapper.fromId(null)).isNull();
    }
}
