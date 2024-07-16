package com.example.securityproject.exception;

public class InvalidCredentials extends RuntimeException{
    public InvalidCredentials() {
    }

    public InvalidCredentials(String message) {
        super(message);
    }
}
