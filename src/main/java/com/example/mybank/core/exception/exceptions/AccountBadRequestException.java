package com.example.mybank.core.exception.exceptions;

public class AccountBadRequestException extends BadRequestException {
    public AccountBadRequestException(String message) {
        super(message);
    }
}