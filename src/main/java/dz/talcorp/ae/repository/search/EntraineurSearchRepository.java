package dz.talcorp.ae.repository.search;

import dz.talcorp.ae.domain.Entraineur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Entraineur entity.
 */
public interface EntraineurSearchRepository extends ElasticsearchRepository<Entraineur, Long> {
}
