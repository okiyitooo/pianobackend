package com.kanaetochi.pianobackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Document(collection="users")
@Data
public class User {
    @Id
    private String id;
    @NotEmpty( message = "LastName cannot be empty")
    private String lastName;
    @NotEmpty( message = "FirstName cannot be empty")
    private String firstName;
    @Email( message = "Email should be valid")
    @Indexed(unique = true)
    private String email;
    @Pattern(regexp = "//d{10}", message = "Phone number must be 10 digits")
    private String phone;
    @NotEmpty(message = "Sex must not be empty")
    private String sex;
    private String password;

}
