package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.Car;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Car entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findFirstByMatricule(String matricule);

}
