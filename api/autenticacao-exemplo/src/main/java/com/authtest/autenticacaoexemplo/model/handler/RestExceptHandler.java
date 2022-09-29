package com.authtest.autenticacaoexemplo.model.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.authtest.autenticacaoexemplo.model.error.ErrorMessage;
import com.authtest.autenticacaoexemplo.model.exception.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex)
    {

        ErrorMessage error = new ErrorMessage("Not Found", HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
