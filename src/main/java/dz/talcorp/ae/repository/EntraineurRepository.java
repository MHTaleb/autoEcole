package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.Entraineur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Entraineur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntraineurRepository extends JpaRepository<Entraineur, Long> {

}
