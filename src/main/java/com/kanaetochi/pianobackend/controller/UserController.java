package com.kanaetochi.pianobackend.controller;

import com.kanaetochi.pianobackend.dto.UserDTO;
import com.kanaetochi.pianobackend.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(id, userDTO));
    }
    @DeleteMapping("/id/{id}") 
    public ResponseEntity<?> deleteUser(String id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User with id " + id + " deleted successfully");
    }
}
