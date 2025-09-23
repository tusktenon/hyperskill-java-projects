package fitnesstracker.security;

import fitnesstracker.persistence.Application;
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

    private final Map<Application, Integer> counts = new ConcurrentHashMap<>();

    @Scheduled(fixedRate = RESET_INTERVAL)
    public void reset() {
        counts.clear();
    }

    public void countRequest(Application application) {
        if (application.getCategory() == Application.Category.BASIC) {
            int updatedCount =
                    counts.merge(application, 1, (app, count) -> Math.incrementExact(count));
            if (updatedCount > MAX_REQUESTS) {
                throw new RateLimitExceededException();
            }
        }
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public static class RateLimitExceededException extends RuntimeException {}
}
