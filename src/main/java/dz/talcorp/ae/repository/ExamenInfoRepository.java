package dz.talcorp.ae.repository;

import dz.talcorp.ae.domain.ExamenInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExamenInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamenInfoRepository extends JpaRepository<ExamenInfo, Long> {

}
