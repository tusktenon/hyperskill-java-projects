package account.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleValidationError(HttpServletResponse response, ConstraintViolationException e)
            throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }
}
