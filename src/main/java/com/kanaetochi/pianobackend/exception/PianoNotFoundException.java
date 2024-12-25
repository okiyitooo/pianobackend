package com.kanaetochi.pianobackend.exception;

public class PianoNotFoundException extends RuntimeException {
    public PianoNotFoundException(String message){
        super(message);
    }
}
