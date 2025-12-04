package com.banking.portal.exception;

import com.banking.portal.dto.ErrorResponseDTO;
import com.banking.portal.dto.ValidationErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
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

    @ExceptionHandler(AccessForbiddenException.class)
    public ResponseEntity<@NotNull ErrorResponseDTO> unauthorizedAccessException(AccessForbiddenException ex,HttpServletRequest http){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("UNAUTHORIZED_ACCESS", ex.getMessage(), http.getRequestURI(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountInactiveException.class)
    public ResponseEntity<@NotNull ErrorResponseDTO> accountInactiveException(AccountInactiveException ex,HttpServletRequest http){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("INACTIVE_ACCOUNT", ex.getMessage(), http.getRequestURI(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<@NotNull ErrorResponseDTO> insufficientBalanceException(InsufficientBalanceException ex,HttpServletRequest http){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("INSUFFICIENT_BALANCE", ex.getMessage(), http.getRequestURI(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<@NotNull ErrorResponseDTO> generalException(Exception ex, HttpServletRequest http){
        log.error("Unhandled exception on {}: {}", http.getRequestURI(), ex.getMessage(), ex);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("INTERNAL_SERVER_ERROR", ex.getMessage(), http.getRequestURI(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<@NotNull ValidationErrorResponseDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request){
        Map<String, String> fieldErrors = ex.getBindingResult() //.getBindingResult() returns the list of objects that has fieldErrors
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (first, second) -> first));

        ValidationErrorResponseDTO validationErrorResponseDTO = new ValidationErrorResponseDTO("VALIDATION_ERROR", "Invalid request data", request.getRequestURI(), LocalDateTime.now(), fieldErrors);
        log.warn("Validation failed for request {}: {}", request.getRequestURI(), fieldErrors);
        return new ResponseEntity<>(validationErrorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    }


//    @ExceptionHandler()
//    public ResponseEntity<@NotNull ErrorResponseDTO> UserNotFoundException(UsernameNotFoundException ex, HttpServletRequest http){
//        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("USER_NOT_FOUND", "The user doesn't exist!", http.getRequestURI(), LocalDateTime.now());
//        return new ResponseEntity<>(errorResponseDTO,HttpStatus.NOT_FOUND);
//    }
