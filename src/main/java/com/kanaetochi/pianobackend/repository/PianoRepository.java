package com.kanaetochi.pianobackend.repository;

import com.kanaetochi.pianobackend.model.Piano;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PianoRepository extends MongoRepository<Piano, String> {
    
}
