package com.kanaetochi.pianobackend.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String messageString) {
        super(messageString);
    }
}
