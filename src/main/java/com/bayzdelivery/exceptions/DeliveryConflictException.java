package com.bayzdelivery.exceptions;

public class DeliveryConflictException extends RuntimeException{
    public DeliveryConflictException(String message){
        super(message);
    }
}
