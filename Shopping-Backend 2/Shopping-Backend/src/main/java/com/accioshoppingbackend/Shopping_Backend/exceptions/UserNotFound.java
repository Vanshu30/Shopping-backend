package com.accioshoppingbackend.Shopping_Backend.exceptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String message){
        super(message);
    }
}
