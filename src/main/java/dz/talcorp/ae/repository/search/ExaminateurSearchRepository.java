package dz.talcorp.ae.repository.search;

import dz.talcorp.ae.domain.Examinateur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Examinateur entity.
 */
public interface ExaminateurSearchRepository extends ElasticsearchRepository<Examinateur, Long> {
}
