package com.farah.pfa2024.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
//This class handles the creation, validation, and parsing(process of analyzing a string of symbols) of JWT tokens using the io.jsonwebtoken library.
@Component
public class JWTUtils {

    private SecretKey Key;
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000L; // 24 hours

    public JWTUtils() {
        try {
            String secretString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
            byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
            this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Secret Key is invalid",e);
        }
    }

    public String createToken(UserDetails userDetails, Long user_id){
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id",String.valueOf(user_id));
        return Jwts.builder()
                .setClaims(claims)
                .subject(userDetails.getUsername()) //get mail
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

   /* public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername()) //get mail
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }*/

    public String extractUsername(String token){  //extract mail
        return extractClaims(token, Claims::getSubject);
    }

    public Long extractUserId(String token){
        return Long.parseLong(extractClaims(token, claims -> claims.get("user_id",String.class)));
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token); //extract mail
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }

}
