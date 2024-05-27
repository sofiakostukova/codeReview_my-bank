package com.example.mybank.core.exception.exceptions;

public class UserBadRequestException extends BadRequestException {
    public UserBadRequestException(String message) {
        super(message);
    }
}