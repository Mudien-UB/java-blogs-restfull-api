package com.example.s.authentication.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
    
    private final String keyString = "ini-adalah-secret-key-jjwt-minimal-32-karakter";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(keyString.getBytes());

    public String generateAccessToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .signWith(SECRET_KEY)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // 10 menit
                .compact();
    }

    public String generateRefreshToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .signWith(SECRET_KEY)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 menit
                .compact();
    }

    public String extractUserId(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Claims claimToken = Jwts.parser()
                                    .verifyWith(SECRET_KEY)
                                    .build()
                                    .parseSignedClaims(token)
                                    .getPayload();
                                    
                                

            if(claimToken.getSubject() == null || claimToken.getSubject().isEmpty()) {
                throw new JwtException("Token Invalid");
            }
            if ( claimToken.getExpiration() == null || claimToken.getExpiration().before(new Date(System.currentTimeMillis()))) {
                throw new JwtException("Token Expired");
            }
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }


}
