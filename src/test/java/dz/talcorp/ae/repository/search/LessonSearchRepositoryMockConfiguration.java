package dz.talcorp.ae.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of LessonSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class LessonSearchRepositoryMockConfiguration {

    @MockBean
    private LessonSearchRepository mockLessonSearchRepository;

}
