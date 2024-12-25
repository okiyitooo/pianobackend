package com.kanaetochi.pianobackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kanaetochi.pianobackend.dto.PurchaseDTO;
import com.kanaetochi.pianobackend.service.PurchaseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    final private PurchaseService purchaseService;
    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    @GetMapping
    public ResponseEntity<List<PurchaseDTO>> getAllPurchases() {
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }
    @PostMapping
    public ResponseEntity<?> createPurchase(@RequestBody @Valid PurchaseDTO purchaseDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(purchaseService.createPurchase(purchaseDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<PurchaseDTO> getPurchaseById(@PathVariable String id) {
        return ResponseEntity.ok(purchaseService.getPurchaseById(id));
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<PurchaseDTO>> getUserPurchases(@PathVariable String userId) {
        return ResponseEntity.ok(purchaseService.getUserPurchases(userId));
    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deletePurchase(@PathVariable String id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.ok("Purchase with id " + id + " deleted successfully");
    }
}
