package aggregator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class AggregationServiceConfig {

    @Bean
    public List<String> sourceURIs() {
        return List.of("http://localhost:8888", "http://localhost:8889");
    }

    @Bean
    public RestClient client() {
        return RestClient.create();
    }
}
