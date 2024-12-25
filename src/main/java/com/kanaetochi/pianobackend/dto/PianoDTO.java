package com.kanaetochi.pianobackend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PianoDTO {
    private String id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    private String description;
    @Positive(message = "Age must be a positive number")
    private int age;
    @Positive(message = "Price must be a positive number")
    private double price;
    @NotEmpty(message = "Brand cannot be empty")
    private String brand;
    @NotEmpty(message = "Type cannot be empty")
    private String type;
    private int numberOfKeys;
    
}
