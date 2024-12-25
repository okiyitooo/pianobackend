package com.kanaetochi.pianobackend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PurchaseDTO {
    private String id;
    @NotEmpty(message="User id musn't be empty")
    private String userId;
    @NotEmpty(message="Piano id musn't be empty")
    private String pianoId;
    private String date;
}
