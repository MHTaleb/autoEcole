package dz.talcorp.ae.service.impl;

import dz.talcorp.ae.service.CarService;
import dz.talcorp.ae.domain.Car;
import dz.talcorp.ae.repository.CarRepository;
import dz.talcorp.ae.service.dto.CarDTO;
import dz.talcorp.ae.service.mapper.CarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Car.
 */
@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final Logger log = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    /**
     * Save a car.
     *
     * @param carDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CarDTO save(CarDTO carDTO) {
        log.debug("Request to save Car : {}", carDTO);
        Car car = carMapper.toEntity(carDTO);
        car = carRepository.save(car);
        return carMapper.toDto(car);
    }

    /**
     * Get all the cars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cars");
        return carRepository.findAll(pageable).map(carMapper::toDto);
    }

    /**
     * Get one car by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CarDTO> findOne(Long id) {
        log.debug("Request to get Car : {}", id);
        return carRepository.findById(id).map(carMapper::toDto);
    }

    /**
     * Delete the car by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Car : {}", id);
        carRepository.deleteById(id);
    }

    /**
     * check if a matricule is not used in another car
     * 
     * @return true if the matricule is already used
     */
    @Override
    public boolean checkMatriculeUnicity(String matricule) {
        return carRepository.findFirstByMatricule(matricule).isPresent();
    }


    /**
     * this is used to check errors for deletion of a car
     * 
     * @return "car.EK_R_01" if a car have an exam entity relation
     * @return "car.EK_R_02" if a car have a lesson entity relation (creno,circulation)
     */
    @Override
    public String checkCarRelations(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        String errorKey = "";
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            errorKey = car.getExamen().size() > 0 ? "car.EK_R_01" : "";
            if (errorKey.isEmpty())
                errorKey = car.getLessons().size() > 0 ? "car.EK_R_02" : "";
        }
        return "";
    }
}
