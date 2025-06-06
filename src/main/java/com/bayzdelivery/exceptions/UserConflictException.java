package com.bayzdelivery.exceptions;

public class UserConflictException extends  RuntimeException{
    public UserConflictException(String message){
        super(message);
    }
}
