package com.example.demo.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handelNotFoundException(RuntimeException exception, WebRequest request) {
        Map<String, Object> bodyOfResponse = new HashMap<>();
        bodyOfResponse.put("message", exception.getMessage());
        bodyOfResponse.put("error", "Not Found");
        bodyOfResponse.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserAlredyRegisteredException.class)
    public ResponseEntity handleUserPresent(UserAlredyRegisteredException ex) {
        Map<String, Object> bodyOfResponse = new HashMap<>();
        bodyOfResponse.put("message", ex.getMessage());
        bodyOfResponse.put("error", "Found");
        bodyOfResponse.put("status", HttpStatus.FOUND.value());
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleJsonFiledException(RuntimeException ex) {
        Map<String, Object> bodyOfResponse = new HashMap<>();
        bodyOfResponse.put("message", ex.getMessage());
        bodyOfResponse.put("error", "Bad Request");
        bodyOfResponse.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }
}
