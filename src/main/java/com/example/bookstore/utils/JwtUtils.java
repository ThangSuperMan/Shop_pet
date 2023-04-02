// package com.example.bookstore.utils;

// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.concurrent.TimeUnit;
// import java.util.function.Function;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;

// @Component
// public class JwtUtils {
//     @Value("${jwt.secret}")
//     private String jwtAccessKey;

//     Logger logger = LoggerFactory.getLogger(JwtUtils.class);

//     public JwtUtils() {
//         logger.info("jwt access key value from env :>> " + jwtAccessKey);
//     }

//     // public String extractUsername(String token) {
//     // }

//     public String generateToken(UserDetails userDetails) {
//         Map<String, Object> claims = new HashMap<>();
//         return createToken(claims, userDetails);
//     }

//     //retrieve expiration date from jwt token
//     public Date getExpirationDateFromToken(String token) {
//         return getClaimFromToken(token, Claims::getExpiration);
//     }

//     public Boolean isTokenExpired(String token) {
//         final Date expiration = getExpirationDateFromToken(token);
//         return expiration.before(new Date());
//     }

//     public String getUsernameFromToken(String token) {
//         return  getClaimFromToken(token, Claims::getSubject);
//     }

//     public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//         final Claims claims =  getAllClaimsFromToken(token);
//         logger.info("claimsResolver.apply(claims)" + claimsResolver.apply(claims).toString());
//         return claimsResolver.apply(claims);
//     }

//     public Claims getAllClaimsFromToken(String token) {
//         return Jwts.parser().setSigningKey(jwtAccessKey).parseClaimsJws(token).getBody();
//     }

//     public String createToken(Map<String, Object> claims, UserDetails userDetails) {
//         // Claims claims = Jwts.claims().setSubject(u.getUsername());
//         // claims.put("userId", u.getId() + "");
//         // claims.put("role", u.getRole());
//         return Jwts.builder()
//             .setSubject(userDetails.getUsername())
//             .claim("token", userDetails.getAuthorities())
//             .setIssuedAt(new Date(System.currentTimeMillis()))
//             .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
//             .signWith(SignatureAlgorithm.HS256, jwtAccessKey).compact();
//     }

//     // public Boolean validateToken(String token, UserDetails userDetails) {
//     //     final String username = 
//     // }
// }
