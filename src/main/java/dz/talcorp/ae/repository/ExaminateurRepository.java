package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.Examinateur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Examinateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExaminateurRepository extends JpaRepository<Examinateur, Long> {

}
