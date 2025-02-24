package com.ecommerce.project.exception;

public class APIException extends RuntimeException{
    public APIException() { // if there is empty exceptions uses the default value from the handler
    }

    public APIException(String message) { // if there is any message, It can be passed via super keyword
        super(message);
    }
}
