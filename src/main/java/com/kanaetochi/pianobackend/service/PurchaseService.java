package com.kanaetochi.pianobackend.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanaetochi.pianobackend.dto.PurchaseDTO;
import com.kanaetochi.pianobackend.exception.PianoNotFoundException;
import com.kanaetochi.pianobackend.exception.UserNotFoundException;
import com.kanaetochi.pianobackend.model.Purchase;
import com.kanaetochi.pianobackend.repository.PianoRepository;
import com.kanaetochi.pianobackend.repository.PurchaseRepository;
import com.kanaetochi.pianobackend.repository.UserRepository;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final PianoRepository pianoRepository;
    final private ModelMapper modelMapper;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, UserRepository userRepository, PianoRepository pianoRepository, ModelMapper modelMapper) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.pianoRepository = pianoRepository;
        this.modelMapper = modelMapper;
        modelMapper.typeMap(PurchaseDTO.class, Purchase.class).addMappings(
            mapper -> mapper.using(
                ctx -> {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        return dateFormat.parse((String) ctx.getSource());
                    } catch (ParseException e) {
                        return new Date();
                    }
                }
            ).map(PurchaseDTO::getDate, Purchase::setDate));
        modelMapper.typeMap(Purchase.class, PurchaseDTO.class).addMappings(
            mapper -> 
                mapper.using(
                    ctx -> {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        return dateFormat.format((Date) ctx.getSource());
                    }
                ).map(Purchase::getDate, PurchaseDTO::setDate));
        
    }

    public PurchaseDTO createPurchase(PurchaseDTO purchaseDTO) {
        userRepository.findById(purchaseDTO.getUserId()).orElseThrow(()-> new UserNotFoundException("User with id " + purchaseDTO.getUserId() + " not found"));
        pianoRepository.findById(purchaseDTO.getPianoId()) .orElseThrow(() -> new PianoNotFoundException("Piano with id " + purchaseDTO.getPianoId() + " not found"));
        Purchase purchase = modelMapper.map(purchaseDTO, Purchase.class);
        purchase.setDate(new Date());
        purchaseRepository.save(purchase);
        purchaseDTO.setId(purchase.getId());
        return purchaseDTO;
    }
    
    public List<PurchaseDTO> getAllPurchases() {
        return purchaseRepository.findAll().stream().map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)).collect(Collectors.toList());
    }
    
    public PurchaseDTO getPurchaseById(String id) {
        return modelMapper.map(purchaseRepository.findById(id) .orElseThrow(() -> new RuntimeException("Purchase with id " + id + " not found")), PurchaseDTO.class);
    }
    public List<PurchaseDTO> getUserPurchases(String userId) {
        userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id " + userId + " not found"));
        return purchaseRepository.findByUserId(userId).stream().map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)).collect(Collectors.toList());
    }
    public void deletePurchase(String id) {
        if (!purchaseRepository.existsById(id)) {
            throw new RuntimeException("Purchase with id " + id + " not found");
        }
        purchaseRepository.deleteById(id);
    }
}
