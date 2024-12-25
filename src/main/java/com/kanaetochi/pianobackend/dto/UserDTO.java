package com.kanaetochi.pianobackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {
    
    private String id;
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    @Email(message =  "Email should be valid")
    private String email;
    @Pattern(regexp = "//d{10}", message = "Phone number should be 10 digits")
    private String phone;
    @NotEmpty(message = "sex cannot be empty")
    private String sex;
    @NotEmpty(message = "Password cannot be empty")
    private String password;

}
