package aggregator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AggregationServiceConfig {

    @Bean
    public RestClient client() {
        return RestClient.create();
    }
}
