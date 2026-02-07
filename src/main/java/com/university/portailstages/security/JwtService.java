package com.university.portailstages.security;

import com.university.portailstages.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import javax.crypto.SecretKey;



@Service
public class JwtService {
    private static final String SECRET_KEY =
            "STAGE_PLATFORM_SECRET_KEY_2026_12345678901234567890";

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignKey(), Jwts.SIG.HS256)   //FORCER HS256
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())               // même clé + algo
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
