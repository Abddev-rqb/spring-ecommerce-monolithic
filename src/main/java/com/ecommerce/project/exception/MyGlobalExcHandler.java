package com.ecommerce.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice //it is a controller advisor
public class MyGlobalExcHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class) // exception before .class it is derived from the terminal which exposed the error with understandable wording
    public ResponseEntity<Map<String, String>> MyMethodArgumentNotValidException (MethodArgumentNotValidException e){ // maps the string in the terminal which the exception is mapped with.
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err ->{ // gets all the errors which binds with the exceptions then it is looped for each error
            String fieldName = ((FieldError)err).getField(); // field error maps the exception error's string value and getting its field in a stored variable
            String message = err.getDefaultMessage(); // the field's string value is stored in err object and getting its default message.
            response.put(fieldName, message); // updating the response variable with the stored value's ref.
        });
        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> MyResourceNotFoundException (ResourceNotFoundException e){
        String message = e.getMessage();
        APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> MyAPIException(APIException e){
        String message = e.getMessage();
        APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
