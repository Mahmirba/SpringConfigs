package org.example.exceptions;

import org.springframework.http.HttpStatus;

public interface IException {
    HttpStatus getHttpStatus();
}