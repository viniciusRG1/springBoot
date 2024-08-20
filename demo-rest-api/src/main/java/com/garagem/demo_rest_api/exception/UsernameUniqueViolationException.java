package com.garagem.demo_rest_api.exception;

public class UsernameUniqueViolationException extends RuntimeException{
    public UsernameUniqueViolationException(String message) {
        super(message);
    }
}
