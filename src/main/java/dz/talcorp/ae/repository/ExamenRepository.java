package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.Examen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Examen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {

}
