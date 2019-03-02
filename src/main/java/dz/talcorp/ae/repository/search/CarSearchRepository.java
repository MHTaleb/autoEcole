package dz.talcorp.ae.repository.search;

import dz.talcorp.ae.domain.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Car entity.
 */
public interface CarSearchRepository extends ElasticsearchRepository<Car, Long> {
}
