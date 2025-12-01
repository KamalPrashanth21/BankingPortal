package com.banking.portal.exception;

public class ResourceNotFoundException extends RuntimeException{
//    String message;
    public ResourceNotFoundException(String message){
        super(message);
    }
}
