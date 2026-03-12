package com.proyectoIntermodular.Exception.follower;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class FollowerNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(FollowerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> exceptionHandler(FollowerNotFoundException exception) {
        return Map.of("errorMessage", exception.getMessage());
    }
}
