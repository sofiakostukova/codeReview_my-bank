package com.example.mybank.core.exception.handler;

import com.example.mybank.core.exception.exceptions.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException exception) {
        return new ApiError(
            List.of(Arrays.toString(exception.getStackTrace())),
            exception.getMessage(),
            exception.getLocalizedMessage(),
            HttpStatus.NOT_FOUND.toString(),
            LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(final BadRequestException exception) {
        return new ApiError(
            List.of(Arrays.toString(exception.getStackTrace())),
            exception.getMessage(),
            exception.getLocalizedMessage(),
            HttpStatus.BAD_REQUEST.toString(),
            LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final ConflictException exception) {
        return new ApiError(
            List.of(Arrays.toString(exception.getStackTrace())),
            exception.getMessage(),
            exception.getLocalizedMessage(),
            HttpStatus.CONFLICT.toString(),
            LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleValidationException(final DataIntegrityViolationException exception) {
        return new ApiError(
            List.of(Arrays.toString(exception.getStackTrace())),
            exception.getMessage(),
            exception.getLocalizedMessage(),
            HttpStatus.CONFLICT.toString(),
            LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleStatusException(final Exception exception) {
        return new ApiError(
            List.of(Arrays.toString(exception.getStackTrace())),
            exception.getMessage(),
            exception.getLocalizedMessage(),
            HttpStatus.CONFLICT.toString(),
            LocalDateTime.now()
        );
    }
}