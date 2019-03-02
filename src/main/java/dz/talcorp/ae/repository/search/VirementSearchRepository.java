package dz.talcorp.ae.repository.search;

import dz.talcorp.ae.domain.Virement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Virement entity.
 */
public interface VirementSearchRepository extends ElasticsearchRepository<Virement, Long> {
}
