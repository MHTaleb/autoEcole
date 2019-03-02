package dz.talcorp.ae.repository.search;

import dz.talcorp.ae.domain.ExamenInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ExamenInfo entity.
 */
public interface ExamenInfoSearchRepository extends ElasticsearchRepository<ExamenInfo, Long> {
}
