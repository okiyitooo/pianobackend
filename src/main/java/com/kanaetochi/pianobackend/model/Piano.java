package com.kanaetochi.pianobackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Document(collection = "pianos")
@Data
public class Piano {
    @Id
    private String id;
    @NotEmpty(message = "Name can't be empty")
    private String name;
    private String description;
    @Positive(message = "Age must be a positive number")
    private int age;
    @NotEmpty(message = "Brand cannot be empty")
    private String brand;
    private PianoType type;
    @Positive(message = "Price must be a positive number")
    private double price;
}
