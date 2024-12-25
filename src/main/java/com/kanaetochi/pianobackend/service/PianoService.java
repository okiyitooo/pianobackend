package com.kanaetochi.pianobackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanaetochi.pianobackend.dto.PianoDTO;
import com.kanaetochi.pianobackend.exception.PianoNotFoundException;
import com.kanaetochi.pianobackend.model.KeyboardPiano;
import com.kanaetochi.pianobackend.model.Piano;
import com.kanaetochi.pianobackend.model.PianoType;
import com.kanaetochi.pianobackend.repository.PianoRepository;
import com.kanaetochi.pianobackend.utils.ValidationUtils;

@Service
public class PianoService {
    private final PianoRepository pianoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PianoService(PianoRepository pianoRepository, ModelMapper modelMapper) {
        this.modelMapper=modelMapper;
        this.pianoRepository=pianoRepository;
        modelMapper.typeMap(PianoDTO.class, Piano.class).addMappings(mapper ->
            mapper.using(ctx -> PianoType.valueOf((String)ctx.getSource())).map(PianoDTO::getType,Piano::setType));
        modelMapper.typeMap(Piano.class, PianoDTO.class).addMappings(mapper ->
            mapper.using(ctx->((PianoType)ctx.getSource()).toString()).map(Piano::getType, PianoDTO::setType));

    }
    public List<PianoDTO> getAllPianos() {
        return pianoRepository.findAll().stream().map(piano -> modelMapper.map(piano, PianoDTO.class)).collect(Collectors.toList());
    }
    public PianoDTO getPianoById(String id) {
        Piano piano = pianoRepository.findById(id).orElseThrow(()-> new PianoNotFoundException("Piano with id " + id + " not found"));
        return modelMapper.map(piano, PianoDTO.class);
    }
    public PianoDTO createPiano(PianoDTO pianoDTO) {
        try {
            ValidationUtils.validatePiano(pianoDTO);
            Piano piano;
            if (pianoDTO.getType().toLowerCase().equals("keyboard")) {
                piano = modelMapper.map(pianoDTO, KeyboardPiano.class);
            } else {
                piano = modelMapper.map(pianoDTO, Piano.class);
            }
            pianoRepository.save(piano);
            pianoDTO.setId(piano.getId());
            return pianoDTO;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public PianoDTO updatePiano(String id, PianoDTO pianoDTO) {
        try {
            ValidationUtils.validatePiano(pianoDTO);
            Piano piano = pianoRepository.findById(id).orElseThrow(() -> new PianoNotFoundException("Piano with id " + id + " not found"));
            if (pianoDTO.getType().toLowerCase().equals("keyboard")) {
                if (!(piano instanceof KeyboardPiano))
                    throw new IllegalArgumentException("Piano type mismatch, can't convert to keyboard");;;;
            } else if (!pianoDTO.getType().toLowerCase().equals("keyboard") && piano instanceof KeyboardPiano){
                throw new IllegalArgumentException("Piano type mismatch, cannot convert from keyboard");
            }
            modelMapper.map(pianoDTO, piano);
            pianoRepository.save(piano);
            return modelMapper.map(piano, PianoDTO.class);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }
    public void deletePiano(String id) {
        try
        {
            ValidationUtils.validateId(id);
            if (!pianoRepository.existsById(id))
                throw new PianoNotFoundException("Piano with id " + id + " not found");
            pianoRepository.deleteById(id);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}