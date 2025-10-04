package account.controllers;

import account.models.ErrorResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseBody handleValidationError(ConstraintViolationException ex,
                                                   HttpServletRequest request) {
        return ErrorResponseBody.badRequest(request, ex.getMessage());
    }
}
