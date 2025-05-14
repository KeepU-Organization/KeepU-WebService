package com.keepu.webAPI.security;

import com.keepu.webAPI.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Key key=Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${app.jwt.token.expiration:3600000}")
    private long expirationTime;
    public String generateToken(User user) {
        Date now= new Date();
        Date validity=new Date(now.getTime()+expirationTime);

        return Jwts.builder().setSubject(user.getEmail()).setIssuedAt(now).setExpiration(validity).claim("id",user.getId()). signWith(key).compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
