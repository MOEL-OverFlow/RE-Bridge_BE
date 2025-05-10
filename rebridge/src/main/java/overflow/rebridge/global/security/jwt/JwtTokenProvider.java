package overflow.rebridge.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import overflow.rebridge.domain.member.Member;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity}") long accessTokenValidity,
            @Value("${jwt.refresh-token-validity}") long refreshTokenValidity
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
    }

    // Access Token 생성 (role, memberId 포함)
    public String createAccessToken(Member member) {
        return createToken(member.getEmail(), member.getMemberId(), member.getRole().name(), accessTokenValidity);
    }

    // Refresh Token은 email만 넣고 생성
    public String createRefreshToken(String email) {
        return createToken(email, null, null, refreshTokenValidity);
    }

    // 토큰 생성 메서드
    private String createToken(String email, Long memberId, String role, long validity) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validity);

        Claims claims = Jwts.claims().setSubject(email);
        if (memberId != null) claims.put("memberId", memberId);
        if (role != null) claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 email(subject) 추출
    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    // 토큰에서 memberId 추출
    public Long getMemberId(String token) {
        return parseClaims(token).get("memberId", Long.class);
    }

    // 토큰에서 role 추출
    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // 유효성 검사
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT 만료됨");
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("유효하지 않은 JWT");
        }
        return false;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
