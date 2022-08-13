package com.blog.Apiblog.excepciones;

import com.blog.Apiblog.dto.ErrorDetalles;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String,String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nombreCampo = ((FieldError)error).getField();
            String msj = error.getDefaultMessage();
            errores.put(nombreCampo, msj);
        });

        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
