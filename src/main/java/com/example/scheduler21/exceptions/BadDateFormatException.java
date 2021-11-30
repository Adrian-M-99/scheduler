package com.example.scheduler21.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadDateFormatException extends RuntimeException {

    public BadDateFormatException(String date) {
        super(date);
    }
}
