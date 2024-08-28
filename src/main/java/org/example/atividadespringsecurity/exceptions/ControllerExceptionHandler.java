package org.example.atividadespringsecurity.exceptions;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ExceptionDTO> handleJWTCreationException(JWTCreationException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Could not generate token: " + exception.getMessage());
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDTO> handleJWTVerificationException(JWTVerificationException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Could not validate token: " + exception.getMessage());
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}
