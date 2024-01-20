package com.example.customerconnect.service.ex;

public class SameDetailsException extends RuntimeException {
    public SameDetailsException(String massage) {
        super(massage);
    }
}
