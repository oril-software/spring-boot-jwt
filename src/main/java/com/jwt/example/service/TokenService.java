package com.jwt.example.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {

    public static final String SECRET_KEY = "MY_SECRET_KEY_1234556789_SHOULD_BE_LONG_ENOUGH";
    private static final long EXPIRATION_TIME = 3600000; // 1 hour in milliseconds

    public String createToken(ObjectId userId) {
        return Jwts.builder()
                .claim("userId", userId.toHexString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    public String getUserIdFromToken(String token) {
        return (String) extractClaims(token).getPayload().get("userId");
    }

    public boolean isTokenValid(String token) {
        String userId = this.getUserIdFromToken(token);
        return userId != null && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getPayload().getExpiration().before(new Date());
    }

    private Jws<Claims> extractClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

}
