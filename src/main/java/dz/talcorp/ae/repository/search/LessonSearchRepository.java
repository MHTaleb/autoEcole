package dz.talcorp.ae.repository.search;

import dz.talcorp.ae.domain.Lesson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Lesson entity.
 */
public interface LessonSearchRepository extends ElasticsearchRepository<Lesson, Long> {
}
