package fitnesstracker.ratelimiting;

import fitnesstracker.models.Application;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MinimumDelayLimiter implements ApplicationRequestRateLimiter {

    // minimum delay between application requests, in milliseconds
    private final long minDelay;

    private final Map<Application, Instant> requests = new ConcurrentHashMap<>();

    public MinimumDelayLimiter(long minDelay) {
        this.minDelay = minDelay;
    }

    @Override
    public void checkRateLimit(Application application) {
        if (application.getCategory() == Application.Category.BASIC) {
            Instant now = Instant.now();
            Instant previous = requests.put(application, now);
            if (previous != null && previous.plusMillis(minDelay).isAfter(now)) {
                throw new RateLimitExceededException();
            }
        }
    }
}
