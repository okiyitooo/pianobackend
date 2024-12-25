package com.kanaetochi.pianobackend.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service

public class TokenBlacklistService {

    private final Set<String> blackListedTokens = new HashSet<>();
    public void blackListToken(String token) {
       blackListedTokens.add(token);
    }
    public boolean isBlackListed(String token) {
        return blackListedTokens.contains(token);
    }

}
