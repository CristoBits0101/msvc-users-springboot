package com.cryfirock.msvc.users.msvc_users.controllers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class HandlerExceptionController {

    /**
     * Handles 404 errors
     * 
     * @param e
     * @return ResponseEntity
     * @throws NoHandlerFoundException
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> notFoundEx(NoHandlerFoundException e) {
        // New error instance
        Error error = new Error();

        // Initialize the attributes
        error.setDate(new Date());
        error.setError("Api rest no encontrado");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        // Returns a custom 404 error
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

}
