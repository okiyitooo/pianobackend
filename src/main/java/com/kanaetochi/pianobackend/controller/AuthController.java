package com.kanaetochi.pianobackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kanaetochi.pianobackend.config.TokenBlacklistService;
import com.kanaetochi.pianobackend.dto.AuthResponse;
import com.kanaetochi.pianobackend.dto.UserDTO;
import com.kanaetochi.pianobackend.dto.UserLoginDTO;
import com.kanaetochi.pianobackend.exception.UserAlreadyExistsException;
import com.kanaetochi.pianobackend.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    
    private final TokenBlacklistService tokenBlacklistService;

    @Autowired
    public AuthController(AuthService authService, TokenBlacklistService tokenBlacklistService) {
        this.authService=authService;
        this.tokenBlacklistService=tokenBlacklistService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserDTO userDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(userDTO));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(authService.login(userLoginDTO));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout (HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.blackListToken(token);
        }
        return ResponseEntity.ok("Log-Out Successful");
    }
}
