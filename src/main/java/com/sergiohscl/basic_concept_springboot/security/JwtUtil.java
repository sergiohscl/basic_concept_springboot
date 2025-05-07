package com.sergiohscl.basic_concept_springboot.security;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
    private static final String SECRET = "minha-chave-secreta-super-segura-e-longa-1234567890"; // Deve ter no mínimo 256 bits (~32+ caracteres)
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // private static final Key Key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000;

    public static String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(KEY, SignatureAlgorithm.HS256)
            .compact();
    }

    // public static String extractUsername(String token) {
    //     return Jwts.parserBuilder().setSigningKey(KEY).build()
    //         .parseClaimsJws(token).getBody().getSubject();
    // }

    public static String extractUsername(String token) {
        if (token == null || token.trim().isEmpty()) return null;
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(KEY)
                       .build()
                       .parseClaimsJws(token)
                       .getBody()
                       .getSubject();
        } catch (JwtException e) {
            System.out.println("Token inválido: " + e.getMessage());
            return null;
        }
    }
    

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
            
        } catch (JwtException e) {
            return false;
        }
    }
}
