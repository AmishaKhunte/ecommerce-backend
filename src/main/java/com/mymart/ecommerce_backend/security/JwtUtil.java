package com.mymart.ecommerce_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey123456";

    private SecretKey getSignKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );
    }

    public String generateToken(String email) {

        return Jwts.builder()

                .subject(email)

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )

                .signWith(getSignKey())

                .compact();
    }

    public String extractUsername(String token) {

        return Jwts.parser()

                .verifyWith(getSignKey())

                .build()

                .parseSignedClaims(token)

                .getPayload()

                .getSubject();
    }

    public boolean validateToken(
            String token,
            String email
    ) {

        String extractedEmail =
                extractUsername(token);

        return extractedEmail.equals(email);
    }
}