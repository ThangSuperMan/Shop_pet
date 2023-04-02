package com.example.bookstore.services.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardUpLeftHandler;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
    String SECRET_KEY = "2F413F442A472D4B6150645367566B59703373367639792442264529482B4D62";

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
            .signWith(getJwtAccessKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key getJwtAccessKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        System.out.println("keyBytes :>> " + keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
