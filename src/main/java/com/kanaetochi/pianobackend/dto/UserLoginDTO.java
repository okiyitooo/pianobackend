package com.kanaetochi.pianobackend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserLoginDTO {
    
    @NotEmpty(message = "email can't be empty")
    private String email;
    @NotEmpty(message = "Password can't be empty")
    private String password;
}
