package com.clip.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private static final String MESSAGE = "Transaction not found";

    public NotFoundException() {
        super(MESSAGE);
    }
}