package com.banking.portal.exception;

public class EmailAlreadyExistsException extends RuntimeException{ //These are unchecked exceptions handled directly by the global handler

    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
