package aggregator;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableCaching
public class AggregationServiceConfig {

    @Bean
    public WebClient client() {
        return WebClient.create();
    }
}
