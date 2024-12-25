package com.kanaetochi.pianobackend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kanaetochi.pianobackend.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
