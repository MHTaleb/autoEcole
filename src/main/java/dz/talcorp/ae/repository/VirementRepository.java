package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.Virement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Virement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VirementRepository extends JpaRepository<Virement, Long> {

}
