package dz.talcorp.ae.repository.search;

import dz.talcorp.ae.domain.Examen;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Examen entity.
 */
public interface ExamenSearchRepository extends ElasticsearchRepository<Examen, Long> {
}
