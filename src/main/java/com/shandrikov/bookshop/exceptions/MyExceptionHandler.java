package com.shandrikov.bookshop.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public void handleInvalidPath(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), "Required path variable 'id' is not present.");
    }

//    Example: The same as previous method but catches bean validation exceptions on service layer
//    @ExceptionHandler(ConstraintViolationException.class)
//    public void handleConstraintViolationException(ConstraintViolationException ex, HttpServletResponse response)
//            throws IOException {
//        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
//        StringBuilder message = new StringBuilder();
//        for (ConstraintViolation violation : violations) {
//            message.append(violation.getMessage()).append(", ");
//        }
//        response.sendError(HttpStatus.BAD_REQUEST.value(), (message.substring(0, message.length() - 2)));
//    }
}
