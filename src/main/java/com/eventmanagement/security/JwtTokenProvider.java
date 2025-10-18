package com.eventmanagement.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;

    // Generate JWT token
    public String generateTokenString(String username, List<String> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()                // ✅ Corrected from jWts
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();                   // ✅ lowercase 'compact()'
    }

    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jwts.parserBuilder()            // ✅ Corrected from jWts
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token); // ✅ Corrected from parseClamisJws
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT expired: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("JWT validation failed: " + ex.getMessage());
        }
        return false;
    }

    // Extract username from token
    public String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    // Extract roles from token
    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaims(token);
        return (List<String>) claims.get("roles");
    }

    // Get claims from token
    private Claims getClaims(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
  
      
