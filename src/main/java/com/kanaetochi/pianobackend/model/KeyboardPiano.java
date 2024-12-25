package com.kanaetochi.pianobackend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Document(collection = "pianos")
@Data
@EqualsAndHashCode(callSuper = true)
public class KeyboardPiano extends Piano {
    @NotNull(message = "Number of keys can't be null")  
    @Min(value = 21, message = "Number of keys can't be less than 21")
    @Max(value = 88, message = "Number of keys can't be more than 88")
    private int numberOfKeys;

    public KeyboardPiano(){
        setType(PianoType.KEYBOARD);
    }
}
