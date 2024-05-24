package com.example.userservice.exception;

public class UserNotFound extends RuntimeException{

    public UserNotFound(String message){
        super(message);
    }
}
