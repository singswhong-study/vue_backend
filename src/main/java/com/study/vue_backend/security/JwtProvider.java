package com.study.vue_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key SECRET_KEY;
//    private static final long ACCESS_TOKEN_VALID = 3 * 60 * 1000L;
//
//    private static final long REFRESH_TOKEN_VALID = 7 * 24 * 60 * 60 * 1000L;
    private static final long ACCESS_TOKEN_VALID = 1 * 1000L;

    private static final long REFRESH_TOKEN_VALID = 20 * 1000L;

    //기본 비밀 키 생성
    public JwtProvider() {
        this.SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateAccessToken(Long memberId, String email, String memberName) {

        Date now = new Date();
        Date expired = new Date(now.getTime() + ACCESS_TOKEN_VALID);

        return Jwts.builder()
                .setSubject(String.valueOf(memberId))   // sub
                .claim("name", memberName)        // custom claim
                .claim("email", email)
                .setIssuedAt(now)                       // iat. 발급시간
                .setExpiration(expired)                 // exp. 만료시간
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // HS256 비밀키 서명
                .compact();

    }

    public String generateRefreshToken(Long memberId, String email, String memberName) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + REFRESH_TOKEN_VALID);
        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .claim("name", memberName)
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)          // 서명 검증을 위한 비밀 키 설정
                .build()
                .parseClaimsJws(token)              // 토큰을 파싱하고 서명을 검증
                .getBody();                         // 클레임(Payload) 부분 반환
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException ex) {
            System.out.println("Invalid JWT signature");
        } catch (io.jsonwebtoken.MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (io.jsonwebtoken.UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
}
