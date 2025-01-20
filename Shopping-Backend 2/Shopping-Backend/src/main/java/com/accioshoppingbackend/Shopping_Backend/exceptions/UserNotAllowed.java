package com.accioshoppingbackend.Shopping_Backend.exceptions;


public class UserNotAllowed extends RuntimeException {
    public UserNotAllowed(String message){
        super(message);
    }
}
