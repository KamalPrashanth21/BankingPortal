package com.banking.portal.exception;

public class AccountInactiveException extends RuntimeException {
    public AccountInactiveException(String message){
        super(message);
    }
}
