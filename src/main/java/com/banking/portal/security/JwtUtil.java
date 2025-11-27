package com.banking.portal.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationTime}")
    private long expirationTime;

    public SecretKey getCryptographicKey(){ //Used to convert the secret string into a key that could be used for signing purposes.
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    private Claims extractAllClaims(String token){ //Used to retrieve the payload(claims) and access all its data
        return Jwts.parser()
                .verifyWith(getCryptographicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(getCryptographicKey())
                .compact();
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username){
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

}
