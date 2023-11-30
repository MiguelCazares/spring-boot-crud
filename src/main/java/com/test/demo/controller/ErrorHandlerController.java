package com.test.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        String type = e.getClass().getSimpleName();
        return switch (type) {
            case "NoSuchElementException" ->
                    new ResponseEntity<>(new CustomException("error", "Product not found"), HttpStatus.NOT_FOUND);
            case "IllegalArgumentException" ->
                    new ResponseEntity<>(new CustomException("error", "Invalid request"), HttpStatus.BAD_REQUEST);
            default ->
                    new ResponseEntity<>(new CustomException("error", "Something went wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }
}

class CustomException {
    private String status;
    private String message;
    private String code;

    public CustomException(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}

