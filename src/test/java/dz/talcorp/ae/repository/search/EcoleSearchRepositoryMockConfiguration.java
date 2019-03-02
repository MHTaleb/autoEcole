package dz.talcorp.ae.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of EcoleSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EcoleSearchRepositoryMockConfiguration {

    @MockBean
    private EcoleSearchRepository mockEcoleSearchRepository;

}
