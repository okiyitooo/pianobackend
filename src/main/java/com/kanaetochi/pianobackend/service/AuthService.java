package com.kanaetochi.pianobackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.kanaetochi.pianobackend.config.JwtService;
import com.kanaetochi.pianobackend.dto.AuthResponse;
import com.kanaetochi.pianobackend.dto.UserDTO;
import com.kanaetochi.pianobackend.dto.UserLoginDTO;
import com.kanaetochi.pianobackend.exception.InvalidCredentialsException;
import com.kanaetochi.pianobackend.exception.UserAlreadyExistsException;
import com.kanaetochi.pianobackend.model.User;
import com.kanaetochi.pianobackend.repository.UserRepository;
import com.kanaetochi.pianobackend.utils.ValidationUtils;

@Service
public class AuthService {
    
    private ModelMapper modelMapper;

    private final UserRepository userRepository;
    
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    @Autowired
    public AuthService(UserRepository userRepository, JwtService jwtService,
            AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.modelMapper=modelMapper;
    }

    public UserDTO signUp(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("A user with this email already exists");
        }
        ValidationUtils.validateEmail(userDTO.getEmail());
        ValidationUtils.validatePassword(userDTO.getPassword());
        ValidationUtils.validateName(userDTO.getFirstName());
        ValidationUtils.validateName(userDTO.getLastName());
        
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);
        return userDTO;
    }

    public AuthResponse login(UserLoginDTO userLoginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));
            User user = userRepository.findByEmail(userLoginDTO.getEmail()).orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
            String jwtToken = jwtService.generateToken(userLoginDTO.getEmail());
            return AuthResponse.builder()
                            .token(jwtToken)
                            .id(user.getId())
                            .build();
        } catch (BadCredentialsException b) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

    }
}
