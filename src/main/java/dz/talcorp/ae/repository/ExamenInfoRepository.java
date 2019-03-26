package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.ExamenInfo;
import dz.talcorp.ae.domain.enumeration.EtatExamen;
import dz.talcorp.ae.domain.enumeration.TypeExamen;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the ExamenInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamenInfoRepository extends JpaRepository<ExamenInfo, Long> {

    // to check if candidat have a exam relation
    Optional<ExamenInfo> findFirstByCandidatId(long id);

	Optional<ExamenInfo> findFirstByCandidatIdAndEtat(long candidatId, EtatExamen etatExamen);

	Optional<ExamenInfo> findFirstByCandidatIdAndEtatAndType(long candidatId, EtatExamen reussi, TypeExamen code);
}
