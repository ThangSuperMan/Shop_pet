package com.example.shop_pet.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    String SECRET_KEY;

    public JwtUtils() {
      logger.info("Secret key :>> " + SECRET_KEY);
    }

    public String extractUsername(String token) {
      logger.info("extractUsername is running...");
      return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      logger.info("extractClaim is running...");
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
      logger.warn("extractAllClaims triggered!");
      return Jwts
        .parserBuilder()
        .setSigningKey(getJwtAccessKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }
    
    public Date extractExpiration(String token) {
      logger.info("extractExpiration triggered!");
      return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
      final String username = extractUsername(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(String username) {
      Map<String, Object> claims = new HashMap<>();
      return createToken(claims, username);
    }
    
    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, username);
    }
    
    public String createRefreshToken(Map<String, Object> claims, String username) {
      return Jwts.builder()
              .setClaims(claims)
              .setSubject(username)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
              .signWith(getJwtAccessKey(), SignatureAlgorithm.HS256)
              .compact();
    }

    public String createToken(Map<String, Object> claims, String username) {
      return Jwts.builder()
              .setClaims(claims)
              .setSubject(username)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              // .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60)))
              // 5 milisecons
              .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60)))
              .signWith(getJwtAccessKey(), SignatureAlgorithm.HS256)
              .compact();
    }

    private Key getJwtAccessKey() {
      byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
      return Keys.hmacShaKeyFor(keyBytes);
    }
}
