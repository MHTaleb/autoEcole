package dz.talcorp.ae.web.rest;

import dz.talcorp.ae.AutoEcoleApp;

import dz.talcorp.ae.domain.Lesson;
import dz.talcorp.ae.domain.Candidat;
import dz.talcorp.ae.domain.Entraineur;
import dz.talcorp.ae.repository.LessonRepository;
import dz.talcorp.ae.repository.search.LessonSearchRepository;
import dz.talcorp.ae.service.LessonService;
import dz.talcorp.ae.service.dto.LessonDTO;
import dz.talcorp.ae.service.mapper.LessonMapper;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static dz.talcorp.ae.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dz.talcorp.ae.domain.enumeration.TypeLesson;
import dz.talcorp.ae.domain.enumeration.EtatLesson;
/**
 * Test class for the LessonResource REST controller.
 *
 * @see LessonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoEcoleApp.class)
public class LessonResourceIntTest {

    private static final TypeLesson DEFAULT_TYPE_LESSON = TypeLesson.CODE;
    private static final TypeLesson UPDATED_TYPE_LESSON = TypeLesson.CRENO;

    private static final LocalDate DEFAULT_DATE_LESSON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LESSON = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_HEUR_LESSON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEUR_LESSON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final EtatLesson DEFAULT_ETAT_LESSON = EtatLesson.PLANIFIER;
    private static final EtatLesson UPDATED_ETAT_LESSON = EtatLesson.VALIDER;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonMapper lessonMapper;

    @Autowired
    private LessonService lessonService;

    /**
     * This repository is mocked in the dz.talcorp.ae.repository.search test package.
     *
     * @see dz.talcorp.ae.repository.search.LessonSearchRepositoryMockConfiguration
     */
    @Autowired
    private LessonSearchRepository mockLessonSearchRepository;

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

    private MockMvc restLessonMockMvc;

    private Lesson lesson;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LessonResource lessonResource = new LessonResource(lessonService);
        this.restLessonMockMvc = MockMvcBuilders.standaloneSetup(lessonResource)
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
    public static Lesson createEntity(EntityManager em) {
        Lesson lesson = new Lesson()
            .typeLesson(DEFAULT_TYPE_LESSON)
            .dateLesson(DEFAULT_DATE_LESSON)
            .heurLesson(DEFAULT_HEUR_LESSON)
            .etatLesson(DEFAULT_ETAT_LESSON)
            .observation(DEFAULT_OBSERVATION);
        // Add required entity
        Candidat candidat = CandidatResourceIntTest.createEntity(em);
        em.persist(candidat);
        em.flush();
        lesson.setCandidat(candidat);
        // Add required entity
        Entraineur entraineur = EntraineurResourceIntTest.createEntity(em);
        em.persist(entraineur);
        em.flush();
        lesson.setEntraineur(entraineur);
        return lesson;
    }

    @Before
    public void initTest() {
        lesson = createEntity(em);
    }

    @Test
    @Transactional
    public void createLesson() throws Exception {
        int databaseSizeBeforeCreate = lessonRepository.findAll().size();

        // Create the Lesson
        LessonDTO lessonDTO = lessonMapper.toDto(lesson);
        restLessonMockMvc.perform(post("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isCreated());

        // Validate the Lesson in the database
        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeCreate + 1);
        Lesson testLesson = lessonList.get(lessonList.size() - 1);
        assertThat(testLesson.getTypeLesson()).isEqualTo(DEFAULT_TYPE_LESSON);
        assertThat(testLesson.getDateLesson()).isEqualTo(DEFAULT_DATE_LESSON);
        assertThat(testLesson.getHeurLesson()).isEqualTo(DEFAULT_HEUR_LESSON);
        assertThat(testLesson.getEtatLesson()).isEqualTo(DEFAULT_ETAT_LESSON);
        assertThat(testLesson.getObservation()).isEqualTo(DEFAULT_OBSERVATION);

        // Validate the Lesson in Elasticsearch
        verify(mockLessonSearchRepository, times(1)).save(testLesson);
    }

    @Test
    @Transactional
    public void createLessonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lessonRepository.findAll().size();

        // Create the Lesson with an existing ID
        lesson.setId(1L);
        LessonDTO lessonDTO = lessonMapper.toDto(lesson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLessonMockMvc.perform(post("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lesson in the database
        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeCreate);

        // Validate the Lesson in Elasticsearch
        verify(mockLessonSearchRepository, times(0)).save(lesson);
    }

    @Test
    @Transactional
    public void checkTypeLessonIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonRepository.findAll().size();
        // set the field null
        lesson.setTypeLesson(null);

        // Create the Lesson, which fails.
        LessonDTO lessonDTO = lessonMapper.toDto(lesson);

        restLessonMockMvc.perform(post("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isBadRequest());

        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateLessonIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonRepository.findAll().size();
        // set the field null
        lesson.setDateLesson(null);

        // Create the Lesson, which fails.
        LessonDTO lessonDTO = lessonMapper.toDto(lesson);

        restLessonMockMvc.perform(post("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isBadRequest());

        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeurLessonIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonRepository.findAll().size();
        // set the field null
        lesson.setHeurLesson(null);

        // Create the Lesson, which fails.
        LessonDTO lessonDTO = lessonMapper.toDto(lesson);

        restLessonMockMvc.perform(post("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isBadRequest());

        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtatLessonIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonRepository.findAll().size();
        // set the field null
        lesson.setEtatLesson(null);

        // Create the Lesson, which fails.
        LessonDTO lessonDTO = lessonMapper.toDto(lesson);

        restLessonMockMvc.perform(post("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isBadRequest());

        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLessons() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        // Get all the lessonList
        restLessonMockMvc.perform(get("/api/lessons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lesson.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeLesson").value(hasItem(DEFAULT_TYPE_LESSON.toString())))
            .andExpect(jsonPath("$.[*].dateLesson").value(hasItem(DEFAULT_DATE_LESSON.toString())))
            .andExpect(jsonPath("$.[*].heurLesson").value(hasItem(DEFAULT_HEUR_LESSON.toString())))
            .andExpect(jsonPath("$.[*].etatLesson").value(hasItem(DEFAULT_ETAT_LESSON.toString())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION.toString())));
    }
    
    @Test
    @Transactional
    public void getLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        // Get the lesson
        restLessonMockMvc.perform(get("/api/lessons/{id}", lesson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lesson.getId().intValue()))
            .andExpect(jsonPath("$.typeLesson").value(DEFAULT_TYPE_LESSON.toString()))
            .andExpect(jsonPath("$.dateLesson").value(DEFAULT_DATE_LESSON.toString()))
            .andExpect(jsonPath("$.heurLesson").value(DEFAULT_HEUR_LESSON.toString()))
            .andExpect(jsonPath("$.etatLesson").value(DEFAULT_ETAT_LESSON.toString()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLesson() throws Exception {
        // Get the lesson
        restLessonMockMvc.perform(get("/api/lessons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        int databaseSizeBeforeUpdate = lessonRepository.findAll().size();

        // Update the lesson
        Lesson updatedLesson = lessonRepository.findById(lesson.getId()).get();
        // Disconnect from session so that the updates on updatedLesson are not directly saved in db
        em.detach(updatedLesson);
        updatedLesson
            .typeLesson(UPDATED_TYPE_LESSON)
            .dateLesson(UPDATED_DATE_LESSON)
            .heurLesson(UPDATED_HEUR_LESSON)
            .etatLesson(UPDATED_ETAT_LESSON)
            .observation(UPDATED_OBSERVATION);
        LessonDTO lessonDTO = lessonMapper.toDto(updatedLesson);

        restLessonMockMvc.perform(put("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isOk());

        // Validate the Lesson in the database
        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeUpdate);
        Lesson testLesson = lessonList.get(lessonList.size() - 1);
        assertThat(testLesson.getTypeLesson()).isEqualTo(UPDATED_TYPE_LESSON);
        assertThat(testLesson.getDateLesson()).isEqualTo(UPDATED_DATE_LESSON);
        assertThat(testLesson.getHeurLesson()).isEqualTo(UPDATED_HEUR_LESSON);
        assertThat(testLesson.getEtatLesson()).isEqualTo(UPDATED_ETAT_LESSON);
        assertThat(testLesson.getObservation()).isEqualTo(UPDATED_OBSERVATION);

        // Validate the Lesson in Elasticsearch
        verify(mockLessonSearchRepository, times(1)).save(testLesson);
    }

    @Test
    @Transactional
    public void updateNonExistingLesson() throws Exception {
        int databaseSizeBeforeUpdate = lessonRepository.findAll().size();

        // Create the Lesson
        LessonDTO lessonDTO = lessonMapper.toDto(lesson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLessonMockMvc.perform(put("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lesson in the database
        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Lesson in Elasticsearch
        verify(mockLessonSearchRepository, times(0)).save(lesson);
    }

    @Test
    @Transactional
    public void deleteLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        int databaseSizeBeforeDelete = lessonRepository.findAll().size();

        // Delete the lesson
        restLessonMockMvc.perform(delete("/api/lessons/{id}", lesson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Lesson in Elasticsearch
        verify(mockLessonSearchRepository, times(1)).deleteById(lesson.getId());
    }

    @Test
    @Transactional
    public void searchLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);
        when(mockLessonSearchRepository.search(queryStringQuery("id:" + lesson.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(lesson), PageRequest.of(0, 1), 1));
        // Search the lesson
        restLessonMockMvc.perform(get("/api/_search/lessons?query=id:" + lesson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lesson.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeLesson").value(hasItem(DEFAULT_TYPE_LESSON.toString())))
            .andExpect(jsonPath("$.[*].dateLesson").value(hasItem(DEFAULT_DATE_LESSON.toString())))
            .andExpect(jsonPath("$.[*].heurLesson").value(hasItem(DEFAULT_HEUR_LESSON.toString())))
            .andExpect(jsonPath("$.[*].etatLesson").value(hasItem(DEFAULT_ETAT_LESSON.toString())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lesson.class);
        Lesson lesson1 = new Lesson();
        lesson1.setId(1L);
        Lesson lesson2 = new Lesson();
        lesson2.setId(lesson1.getId());
        assertThat(lesson1).isEqualTo(lesson2);
        lesson2.setId(2L);
        assertThat(lesson1).isNotEqualTo(lesson2);
        lesson1.setId(null);
        assertThat(lesson1).isNotEqualTo(lesson2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LessonDTO.class);
        LessonDTO lessonDTO1 = new LessonDTO();
        lessonDTO1.setId(1L);
        LessonDTO lessonDTO2 = new LessonDTO();
        assertThat(lessonDTO1).isNotEqualTo(lessonDTO2);
        lessonDTO2.setId(lessonDTO1.getId());
        assertThat(lessonDTO1).isEqualTo(lessonDTO2);
        lessonDTO2.setId(2L);
        assertThat(lessonDTO1).isNotEqualTo(lessonDTO2);
        lessonDTO1.setId(null);
        assertThat(lessonDTO1).isNotEqualTo(lessonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lessonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lessonMapper.fromId(null)).isNull();
    }
}
