package com.kanaetochi.pianobackend.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.kanaetochi.pianobackend.dto.PianoDTO;
import com.kanaetochi.pianobackend.dto.UserDTO;

@Component
public class ValidationUtils {
    public static Map<String,String> getValidationErrors(BindingResult result) {
        Map<String,String > errors = new HashMap<>();
        if (result.hasErrors())
            for (FieldError err : result.getFieldErrors()) {
                errors.put(err.getField(), err.getDefaultMessage());
            }
        return errors;
    }
    public static void validatePassword(String password ){
        // passwords should be at least 8 characters long
        if (password.length() < 8)
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        // passwords should contain at least 3 digits and 3 special characters
        int digits = 0;
        int specials = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) digits++;
            if (!Character.isLetterOrDigit(c)) specials++;
        }
        if (digits < 3 || specials < 3)
            throw new IllegalArgumentException("Password must contain at least 3 digits and 3 special characters");
        // passwords should not have more than 3 consecutive repeating characters
        int count = 1;
        char prev = password.charAt(0);
        for (int i = 1; i < password.length(); i++) {
            if (password.charAt(i) == prev) {
                count++;
                if (count > 3)
                    throw new IllegalArgumentException("Password should not have more than 3 consecutive repeating characters");
            } else {
                count = 1;
                prev = password.charAt(i);
            }
        }
    }
    public static void validateEmail(String email) {
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            throw new IllegalArgumentException("Invalid email address");
    }
    public static void validateUsername(String username) {
        if (!username.matches("^[a-zA-Z0-9._-]{3,}$"))
            throw new IllegalArgumentException("Username must be at least 3 characters long and can only contain letters, numbers, underscores, and hyphens");
    }
    public static void validateName(String name) {
        if (!name.matches("^[a-zA-Z]{2,}$"))
            throw new IllegalArgumentException("Name must be at least 2 characters long and can only contain letters");
    }
    public static void validatePiano(PianoDTO pianoDTO) {
        if (pianoDTO.getBrand() == null || pianoDTO.getBrand().isEmpty())
            throw new IllegalArgumentException("Brand is required");
        if (pianoDTO.getType() == null || pianoDTO.getType().isEmpty())
            throw new IllegalArgumentException("Type is required");
        if (pianoDTO.getAge() < 0)
            throw new IllegalArgumentException("Age must be a positive number");
        if (pianoDTO.getPrice() < 0)
            throw new IllegalArgumentException("Price must be a positive number");
    }
    public static void validateUser(UserDTO userDTO) {
        validateName(userDTO.getFirstName());
        validateName(userDTO.getLastName());
        validateEmail(userDTO.getEmail());
        validatePassword(userDTO.getPassword());
    }
    public static void validateId(String id) {
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("ID is required");
        if (!id.matches("^[0-9a-fA-F]{24}$"))
            throw new IllegalArgumentException("Invalid ID");
    }
    public static String getFormattedErrorMessage(FieldError error, String code) {
        return code + ":" + error.getDefaultMessage();
    }
}
