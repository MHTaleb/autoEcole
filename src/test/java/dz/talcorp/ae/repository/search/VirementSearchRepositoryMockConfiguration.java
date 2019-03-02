package dz.talcorp.ae.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of VirementSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class VirementSearchRepositoryMockConfiguration {

    @MockBean
    private VirementSearchRepository mockVirementSearchRepository;

}
