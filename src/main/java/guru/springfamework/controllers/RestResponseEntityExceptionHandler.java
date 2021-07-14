package guru.springfamework.controllers;

import guru.springfamework.services.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice("guru.springfamework.controllers.v1")
    public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest webRequest){
        return new ResponseEntity<>("Resource not found!",new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
