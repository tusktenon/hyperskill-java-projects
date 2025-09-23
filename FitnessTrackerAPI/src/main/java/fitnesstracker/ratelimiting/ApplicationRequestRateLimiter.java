package fitnesstracker.ratelimiting;

import fitnesstracker.persistence.Application;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface ApplicationRequestRateLimiter {

    void checkRateLimit(Application application) throws RateLimitExceededException;

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public static class RateLimitExceededException extends RuntimeException {}
}
