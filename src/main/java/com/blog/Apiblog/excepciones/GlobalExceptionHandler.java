package com.blog.Apiblog.excepciones;

import com.blog.Apiblog.dto.ErrorDetalles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetalles> manejarResourceNotFoundException(ResourceNotFoundException e,
                                                                          WebRequest webRequest){

        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(), e.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetalles, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAppExcepcion.class)
    public ResponseEntity<ErrorDetalles> manejarBlogAppExcepcion(BlogAppExcepcion e,
                                                                 WebRequest webRequest){

        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(), e.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetalles, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarGlobalExcepcion(Exception e,
                                                                 WebRequest webRequest){

        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(), e.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
