package fitnesstracker.ratelimiting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling // required for RequestsPerIntervalLimiter
public class RateLimitingConfig {

    @Bean
    public ApplicationRequestRateLimiter applicationRequestRateLimiter() {
        return new MinimumDelayLimiter(1000);
    }
}
