package com.example.ipfsmaven.controller;


import com.example.ipfsmaven.dto.ExceptionDto;
import com.example.ipfsmaven.exception.NodeNotFoundException;
import com.example.ipfsmaven.exception.NodeUpperException;
import com.example.ipfsmaven.exception.PagginationException;
import com.example.ipfsmaven.validation.ValidationErrorResponse;
import com.example.ipfsmaven.validation.Violation;
import com.google.gson.Gson;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(NodeUpperException.class)
    public ResponseEntity<?> handleNoDataFormatError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new Gson().toJson(new ExceptionDto("Нода уже существует")),headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NodeNotFoundException.class)
    public ResponseEntity<?> handleNoDataError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new Gson().toJson(new ExceptionDto("Не удалось найти Ноду")),headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PagginationException.class)
    public ResponseEntity<?> handlePagginationError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new Gson().toJson(new ExceptionDto("Такой параметр Offset невозможен")),headers, HttpStatus.BAD_REQUEST);
    }



    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e
    ) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }



}