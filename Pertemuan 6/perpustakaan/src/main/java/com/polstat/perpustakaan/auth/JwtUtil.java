package com.polstat.perpustakaan.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.Date;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Component
public class JwtUtil implements Serializable {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRE_DURATION;

    public String generateAccessToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer("Polstat")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT expired" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Token is null, empty or only whitespace" +
                    ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.out.println("JWT is invalid" + ex);
        } catch (UnsupportedJwtException ex) {
            System.out.println("JWT is not supported" + ex);
        } catch (SignatureException ex) {
            System.out.println("Signature validation failed");
        }
        return false;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}