package com.shandrikov.bookshop.exceptions;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.shandrikov.bookshop.utils.StringPool.MISS_PATH_VARIABLE;

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
        response.sendError(HttpStatus.NOT_FOUND.value(), MISS_PATH_VARIABLE);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidAuth(BadCredentialsException ex){
        return ResponseEntity.status(401).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Map<String, String>> handleInvalidJwt(JwtException ex){
        return ResponseEntity.status(401).body(Map.of("error", ex.getMessage()));
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
