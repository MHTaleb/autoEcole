package dz.talcorp.ae.repository.search;

import dz.talcorp.ae.domain.Ecole;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Ecole entity.
 */
public interface EcoleSearchRepository extends ElasticsearchRepository<Ecole, Long> {
}
