package com.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandling {
    //handle specific Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleException(ResourceNotFoundException exc,
                                                        WebRequest webRequest){
        ErrorDetails error = new ErrorDetails();
        //here i'm using setters, we can also use constructors for injecting values
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setDetails(webRequest.getDescription(false));
        error.setTimeStamp(new Date());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    //global Exception
    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetails> handleBlogException(BlogApiException exc,
                                                            WebRequest webRequest){
        ErrorDetails error = new ErrorDetails(new Date(),
                exc.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    //anyOther Exception apart from above
    @ExceptionHandler(Exception.class) //All OtherException clases internally extends Exception Class
    public ResponseEntity<ErrorDetails> handleAllOtherException(Exception exc,
                                                                WebRequest webRequest){
        ErrorDetails error = new ErrorDetails();
        error.setTimeStamp(new Date());
        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setDetails(webRequest.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
