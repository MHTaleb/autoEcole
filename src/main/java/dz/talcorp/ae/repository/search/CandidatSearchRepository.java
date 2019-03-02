package dz.talcorp.ae.repository.search;

import dz.talcorp.ae.domain.Candidat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Candidat entity.
 */
public interface CandidatSearchRepository extends ElasticsearchRepository<Candidat, Long> {
}
