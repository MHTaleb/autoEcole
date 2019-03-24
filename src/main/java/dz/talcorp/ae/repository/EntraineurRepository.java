package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.Entraineur;
import dz.talcorp.ae.service.dto.EntraineurDTO;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Entraineur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntraineurRepository extends JpaRepository<Entraineur, Long> {

	Optional<Entraineur> findFirstByTelephone(String telephone);

}
