package com.br.Lojinha.Lojinha.util;

import org.springframework.http.HttpStatus;

public class ExceptionResponse extends Exception {

    private String message;
    private HttpStatus status;

    public ExceptionResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
