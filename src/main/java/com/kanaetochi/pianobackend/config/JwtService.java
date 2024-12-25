package com.kanaetochi.pianobackend.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration}")
    private long EXPIRATION;

    public String generateToken(String email) {
        return generateToken(new HashMap<>(), email);
    }

    private String generateToken(Map<String, Object> extraClaims, String email) {
        SecureDigestAlgorithm<SecretKey, ?> algorithm = Jwts.SIG.HS256;
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(getSignInKey(), algorithm)
                .compact();
    }
    private SecretKey getSignInKey() { 
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExipration(token).before(new Date());
    }

    private Date extractExipration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
}
