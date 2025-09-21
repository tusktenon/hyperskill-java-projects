package fitnesstracker.security;

import fitnesstracker.persistence.Application;
import fitnesstracker.persistence.ApplicationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationRequestRateLimiter {

    // period between request count resets, in milliseconds
    final static int RESET_INTERVAL = 1000;

    // maximum number of requests between resets
    // NOTE: request rate limit (in requests per second) is 1000 * MAX_REQUESTS / RESET_INTERVAL
    final static int MAX_REQUESTS = 1;

    private final Map<Application, Integer> counters = new ConcurrentHashMap<>();

    private ApplicationRequestRateLimiter() {
    }

    public static ApplicationRequestRateLimiter initialize(ApplicationRepository repository) {
        var limiter = new ApplicationRequestRateLimiter();
        repository.findAllByCategory(Application.Category.BASIC)
                .forEach(application -> limiter.counters.put(application, 0));
        return limiter;
    }

    public void register(Application application) {
        counters.put(application, 0);
    }

    @Scheduled(fixedRate = RESET_INTERVAL)
    public void reset() {
        counters.forEach(((application, __) -> counters.put(application, 0)));
    }

    public void countRequest(Application application) {
        Integer updatedCount = counters.computeIfPresent(application,
                (__, current) -> Math.min(current + 1, MAX_REQUESTS + 1));
        if (updatedCount != null && updatedCount > MAX_REQUESTS) {
            throw new RateLimitExceededException();
        }
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public static class RateLimitExceededException extends RuntimeException {}
}
