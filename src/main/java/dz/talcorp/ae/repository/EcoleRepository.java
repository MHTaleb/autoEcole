package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.Ecole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ecole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcoleRepository extends JpaRepository<Ecole, Long> {

}
