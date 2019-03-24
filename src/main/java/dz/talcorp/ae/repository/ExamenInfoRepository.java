package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.ExamenInfo;

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
}
