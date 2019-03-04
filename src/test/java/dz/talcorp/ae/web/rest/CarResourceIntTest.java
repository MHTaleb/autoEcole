package dz.talcorp.ae.web.rest;

import dz.talcorp.ae.AutoEcoleApp;

import dz.talcorp.ae.domain.Car;
import dz.talcorp.ae.repository.CarRepository;
import dz.talcorp.ae.repository.search.CarSearchRepository;
import dz.talcorp.ae.service.CarService;
import dz.talcorp.ae.service.dto.CarDTO;
import dz.talcorp.ae.service.mapper.CarMapper;
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
 * Test class for the CarResource REST controller.
 *
 * @see CarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoEcoleApp.class)
public class CarResourceIntTest {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarService carService;

    /**
     * This repository is mocked in the dz.talcorp.ae.repository.search test package.
     *
     * @see dz.talcorp.ae.repository.search.CarSearchRepositoryMockConfiguration
     */
    @Autowired
    private CarSearchRepository mockCarSearchRepository;

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

    private MockMvc restCarMockMvc;

    private Car car;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarResource carResource = new CarResource(carService);
        this.restCarMockMvc = MockMvcBuilders.standaloneSetup(carResource)
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
    public static Car createEntity(EntityManager em) {
        Car car = new Car()
            .matricule(DEFAULT_MATRICULE)
            .marque(DEFAULT_MARQUE);
        return car;
    }

    @Before
    public void initTest() {
        car = createEntity(em);
    }

    @Test
    @Transactional
    public void createCar() throws Exception {
        int databaseSizeBeforeCreate = carRepository.findAll().size();

        // Create the Car
        CarDTO carDTO = carMapper.toDto(car);
        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isCreated());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeCreate + 1);
        Car testCar = carList.get(carList.size() - 1);
        assertThat(testCar.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testCar.getMarque()).isEqualTo(DEFAULT_MARQUE);

        // Validate the Car in Elasticsearch
        verify(mockCarSearchRepository, times(1)).save(testCar);
    }

    @Test
    @Transactional
    public void createCarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carRepository.findAll().size();

        // Create the Car with an existing ID
        car.setId(1L);
        CarDTO carDTO = carMapper.toDto(car);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeCreate);

        // Validate the Car in Elasticsearch
        verify(mockCarSearchRepository, times(0)).save(car);
    }

    @Test
    @Transactional
    public void checkMatriculeIsRequired() throws Exception {
        int databaseSizeBeforeTest = carRepository.findAll().size();
        // set the field null
        car.setMatricule(null);

        // Create the Car, which fails.
        CarDTO carDTO = carMapper.toDto(car);

        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isBadRequest());

        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCars() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        // Get all the carList
        restCarMockMvc.perform(get("/api/cars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE.toString())))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE.toString())));
    }
    
    @Test
    @Transactional
    public void getCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        // Get the car
        restCarMockMvc.perform(get("/api/cars/{id}", car.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(car.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE.toString()))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCar() throws Exception {
        // Get the car
        restCarMockMvc.perform(get("/api/cars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Update the car
        Car updatedCar = carRepository.findById(car.getId()).get();
        // Disconnect from session so that the updates on updatedCar are not directly saved in db
        em.detach(updatedCar);
        updatedCar
            .matricule(UPDATED_MATRICULE)
            .marque(UPDATED_MARQUE);
        CarDTO carDTO = carMapper.toDto(updatedCar);

        restCarMockMvc.perform(put("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isOk());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
        Car testCar = carList.get(carList.size() - 1);
        assertThat(testCar.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testCar.getMarque()).isEqualTo(UPDATED_MARQUE);

        // Validate the Car in Elasticsearch
        verify(mockCarSearchRepository, times(1)).save(testCar);
    }

    @Test
    @Transactional
    public void updateNonExistingCar() throws Exception {
        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Create the Car
        CarDTO carDTO = carMapper.toDto(car);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarMockMvc.perform(put("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Car in Elasticsearch
        verify(mockCarSearchRepository, times(0)).save(car);
    }

    @Test
    @Transactional
    public void deleteCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        int databaseSizeBeforeDelete = carRepository.findAll().size();

        // Delete the car
        restCarMockMvc.perform(delete("/api/cars/{id}", car.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Car in Elasticsearch
        verify(mockCarSearchRepository, times(1)).deleteById(car.getId());
    }

    @Test
    @Transactional
    public void searchCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);
        when(mockCarSearchRepository.search(queryStringQuery("id:" + car.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(car), PageRequest.of(0, 1), 1));
        // Search the car
        restCarMockMvc.perform(get("/api/_search/cars?query=id:" + car.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car.class);
        Car car1 = new Car();
        car1.setId(1L);
        Car car2 = new Car();
        car2.setId(car1.getId());
        assertThat(car1).isEqualTo(car2);
        car2.setId(2L);
        assertThat(car1).isNotEqualTo(car2);
        car1.setId(null);
        assertThat(car1).isNotEqualTo(car2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarDTO.class);
        CarDTO carDTO1 = new CarDTO();
        carDTO1.setId(1L);
        CarDTO carDTO2 = new CarDTO();
        assertThat(carDTO1).isNotEqualTo(carDTO2);
        carDTO2.setId(carDTO1.getId());
        assertThat(carDTO1).isEqualTo(carDTO2);
        carDTO2.setId(2L);
        assertThat(carDTO1).isNotEqualTo(carDTO2);
        carDTO1.setId(null);
        assertThat(carDTO1).isNotEqualTo(carDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(carMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(carMapper.fromId(null)).isNull();
    }
}
