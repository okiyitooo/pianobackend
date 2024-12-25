package com.kanaetochi.pianobackend.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "purchases")
public class Purchase {

    @Id
    private String id;
    private String userId;
    private String pianoId;
    private Date date;
}
