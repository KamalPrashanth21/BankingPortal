package com.banking.portal.exception;

public class UsernameAlreadyExistsException extends RuntimeException{

    public UsernameAlreadyExistsException(String message){
        super(message);
    }

}
