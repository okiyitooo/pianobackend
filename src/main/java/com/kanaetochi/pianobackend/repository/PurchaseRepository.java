package com.kanaetochi.pianobackend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kanaetochi.pianobackend.model.Purchase;

public interface PurchaseRepository extends MongoRepository <Purchase, String> {
    List<Purchase> findByUserId(String userId);
}
