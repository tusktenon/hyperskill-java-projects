package account.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

public record ErrorResponseBody(
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {

    public static ErrorResponseBody badRequest(HttpServletRequest request, String message) {
        return new ErrorResponseBody(
                Instant.now(), 400, "Bad Request", message, request.getRequestURI());
    }

    public static ErrorResponseBody forbidden(HttpServletRequest request) {
        return new ErrorResponseBody(
                Instant.now(), 403, "Forbidden", "Access Denied!", request.getRequestURI());
    }

    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
