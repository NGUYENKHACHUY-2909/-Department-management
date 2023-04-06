package com.vti.project.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.module.FindException;

@RestControllerAdvice
public class HandleExceptionFindNotFound extends Exception{
    @ExceptionHandler
    public String handlerFindNotFound(FindException findException){
        return findException.getMessage();
    }
}
