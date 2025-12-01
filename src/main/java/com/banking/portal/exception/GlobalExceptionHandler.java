package com.banking.portal.exception;

import com.banking.portal.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<@NotNull ErrorResponseDTO> usernameAlreadyExistsException(UsernameAlreadyExistsException ex, HttpServletRequest http){
        ErrorResponseDTO errorDto = new ErrorResponseDTO("USER_CONFLICT",ex.getMessage(),http.getRequestURI(), LocalDateTime.now());
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<@NotNull ErrorResponseDTO> emailAlreadyExistsException(EmailAlreadyExistsException ex, HttpServletRequest http){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("USER_CONFLICT", ex.getMessage(), http.getRequestURI(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO,HttpStatus.CONFLICT);
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class}) //Both are wrapped under a common handler to avoid Username enumeration by attackers
    public ResponseEntity<@NotNull ErrorResponseDTO> invalidCredsException(BadCredentialsException ex, HttpServletRequest http){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("INVALID_CREDENTIALS", "Username or Password is invalid / incorrect!", http.getRequestURI(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class) //Both are wrapped under a common handler to avoid Username enumeration by attackers
    public ResponseEntity<@NotNull ErrorResponseDTO> resourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest http){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("NOT_FOUND", "The requested resource is not found!", http.getRequestURI(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<@NotNull ErrorResponseDTO> generalException(Exception ex, HttpServletRequest http){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("INTERNAL_SERVER_ERROR", ex.getMessage(), http.getRequestURI(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }


//    @ExceptionHandler()
//    public ResponseEntity<@NotNull ErrorResponseDTO> UserNotFoundException(UsernameNotFoundException ex, HttpServletRequest http){
//        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("USER_NOT_FOUND", "The user doesn't exist!", http.getRequestURI(), LocalDateTime.now());
//        return new ResponseEntity<>(errorResponseDTO,HttpStatus.NOT_FOUND);
//    }
}
