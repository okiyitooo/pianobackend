package com.kanaetochi.pianobackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanaetochi.pianobackend.dto.UserDTO;
import com.kanaetochi.pianobackend.exception.UserNotFoundException;
import com.kanaetochi.pianobackend.model.User;
import com.kanaetochi.pianobackend.repository.UserRepository;
import com.kanaetochi.pianobackend.utils.ValidationUtils;

@Service
public class UserService {

    final private UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository=userRepository;
        this.modelMapper=modelMapper;
    }
    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream().map(user->modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }
    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with id " + id + " not found"));
        return modelMapper.map(user, UserDTO.class);
    }
    public UserDTO updateUser(String id, UserDTO userDTO) {
        try {
            ValidationUtils.validateId(id);
            ValidationUtils.validateUser(userDTO);
            User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with id " + id + " not found"));
            modelMapper.map(userDTO, user);
            userRepository.save(user);
            return modelMapper.map(user, UserDTO.class);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
    }
    public void deleteUser(String id) {
        try {
            ValidationUtils.validateId(id);
            userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with id " + id + " not found"));
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }
}
