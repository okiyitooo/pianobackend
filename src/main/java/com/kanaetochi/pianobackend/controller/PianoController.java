package com.kanaetochi.pianobackend.controller;

import com.kanaetochi.pianobackend.dto.PianoDTO;
import com.kanaetochi.pianobackend.exception.PianoNotFoundException;
import com.kanaetochi.pianobackend.service.PianoService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pianos")
public class PianoController {
    private final PianoService pianoService;
    @Autowired
    public PianoController(PianoService pianoService) {
        this.pianoService=pianoService;
    }
    @GetMapping
    public ResponseEntity<List<PianoDTO>> getAllPianos(){
        return ResponseEntity.ok(pianoService.getAllPianos());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPianoById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(pianoService.getPianoById(id));
        } catch(PianoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<PianoDTO> createPiano(@Valid @RequestBody PianoDTO pianoDTO ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pianoService.createPiano(pianoDTO));
    }
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updatePiano(@PathVariable String id , @Valid @RequestBody PianoDTO pianoDTO) {
        try { 
            return ResponseEntity.ok(pianoService.updatePiano(id, pianoDTO)); }
        catch (PianoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deletePiano(@PathVariable String id) {
        pianoService.deletePiano(id);
        return ResponseEntity.ok("Piano with id "+ id + " deleted successfully");
    }
}
