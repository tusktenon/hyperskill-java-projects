package engine;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /* If a request body cannot be parsed as properly formatted JSON, the server should
       respond with 400 BAD REQUEST, not 403 FORBIDDEN. */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleHttpMessageNotReadable() {
    }

    /* If an argument to a controller method fails validation, the server should respond
       with 400 BAD REQUEST, not 401 UNAUTHORIZED or 403 FORBIDDEN. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleMethodArgumentNotValid() {
    }

    /* If a controller method throws a ResponseStatusException, the server should respond
       with the provided response status. */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Void> handleResponseStatusException(ResponseStatusException e) {
        return new ResponseEntity<>(e.getStatusCode());
    }
}
