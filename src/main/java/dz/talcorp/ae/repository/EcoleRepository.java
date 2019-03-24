package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.Ecole;
import dz.talcorp.ae.service.dto.EntraineurDTO;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ecole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcoleRepository extends JpaRepository<Ecole, Long> {
    // check if school name exists
    Optional<Ecole> findFirstByTitreEcole(String titreEcole);
    // check if trainer is a school president
	Optional<Ecole> findFirstByPresidentId(Long id);
}
