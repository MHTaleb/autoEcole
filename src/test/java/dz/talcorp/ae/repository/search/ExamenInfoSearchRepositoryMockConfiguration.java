package dz.talcorp.ae.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ExamenInfoSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ExamenInfoSearchRepositoryMockConfiguration {

    @MockBean
    private ExamenInfoSearchRepository mockExamenInfoSearchRepository;

}
