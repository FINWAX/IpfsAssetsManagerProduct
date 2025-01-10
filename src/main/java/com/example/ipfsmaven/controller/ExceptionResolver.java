package com.example.ipfsmaven.controller;


import com.example.ipfsmaven.dto.ResponseDto;
import com.example.ipfsmaven.exception.NodeNotFoundException;
import com.example.ipfsmaven.exception.NodeUpperException;
import com.example.ipfsmaven.exception.PagginationException;
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



@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(NodeUpperException.class)
    public ResponseEntity<?> handleNoDataFormatError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new Gson().toJson(new ResponseDto(true, "Нода уже существует")), headers, HttpStatus.OK);
    }

    @ExceptionHandler(NodeNotFoundException.class)
    public ResponseEntity<?> handleNoDataError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new Gson().toJson(new ResponseDto(true, "Не удалось найти Ноду")), headers, HttpStatus.OK);
    }

    @ExceptionHandler(PagginationException.class)
    public ResponseEntity<?> handlePagginationError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new Gson().toJson(new ResponseDto(true, "Такой параметр Offset невозможен")), headers, HttpStatus.OK);
    }


    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> onConstraintValidationException() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new Gson().toJson(new ResponseDto(true, "Ошибка валидации")), headers, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> onMethodArgumentNotValidException() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new Gson().toJson(new ResponseDto(true, "Ошибка валидации")), headers, HttpStatus.OK);
    }


}