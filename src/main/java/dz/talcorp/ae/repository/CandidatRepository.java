package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.Candidat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Candidat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long> {

}
