package com.example.demo.exception;

public class UserAlredyRegisteredException extends RuntimeException {
    public UserAlredyRegisteredException(String s) {
        super(s);
    }
}
