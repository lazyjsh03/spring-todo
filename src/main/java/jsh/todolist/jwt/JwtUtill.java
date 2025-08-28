package jsh.todolist.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtill {

    @Value("${jwt.secret.key}")
    private String secretString;

    @Value("${jwt.token.expiration-time}")
    private long expirationTime;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretString);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // 이름을 받아 JWT 생성
    public String generateToken(String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    // JWT를 파싱하여 이름 추출
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try{
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
