package com.nexters.momo.member.auth.jwt;

import com.nexters.momo.member.auth.application.RedisCachingService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenFactory {

    private final RedisCachingService redisCachingService;
    private final Key accessPrivateKey;
    private final Key refreshPrivateKey;
    private final Long accessExpirationMillis;
    private final Long refreshExpirationMillis;

    public JwtTokenFactory(RedisCachingService redisCachingService, JwtProperties jwtProperties) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.redisCachingService = redisCachingService;
        this.accessPrivateKey = getPrivateKey(jwtProperties.getAccessKey());
        this.refreshPrivateKey = getPrivateKey(jwtProperties.getRefreshKey());
        this.accessExpirationMillis = jwtProperties.getAccessLength();
        this.refreshExpirationMillis = jwtProperties.getRefreshLength();
    }

    public JwtToken issue(String userId, List<String> roles) {
        return new JwtToken(createAccessToken(userId, roles), createRefreshToken(userId));
    }

    public String createAccessToken(String userEmail, List<String> roles) {
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + accessExpirationMillis);

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("userEmail", userEmail);

        return Jwts.builder()
                .setSubject(userEmail)
                .setClaims(claims)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(accessPrivateKey)
                .compact();
    }

    public String createRefreshToken(String userEmail) {
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + refreshExpirationMillis);

        String refreshToken = Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(refreshPrivateKey)
                .compact();

        redisCachingService.setValuesWithDuration(userEmail, refreshToken, Duration.ofMillis(refreshExpirationMillis));

        return refreshToken;
    }

    public ResponseCookie createRefreshCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(refreshExpirationMillis)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
    }

    public String getUserIdFromToken(String accessToken) {
        return (String) Jwts.parserBuilder()
                .setSigningKey(accessPrivateKey)
                .build()
                .parseClaimsJws(accessToken).getBody().get("userId");
    }

    public Collection<GrantedAuthority> getRolesFromToken(String accessToken) {
        List<String> roles = (List) Jwts.parserBuilder()
                .setSigningKey(accessPrivateKey)
                .build()
                .parseClaimsJws(accessToken).getBody().get("roles");

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public boolean isValidAccessToken(String accessToken) {
        if (StringUtils.hasText(accessToken)) {
            try {
                Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(accessPrivateKey).build()
                        .parseClaimsJws(accessToken);

                return !claims.getBody().getExpiration().before(new Date());
            } catch (ExpiredJwtException e) {
                return false;
            }
        }
        return false;
    }

    public boolean isValidRefreshToken(String refreshToken) {
        if (StringUtils.hasText(refreshToken)) {
            try {
                Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(refreshPrivateKey).build()
                        .parseClaimsJws(refreshToken);

                return !claims.getBody().getExpiration().before(new Date());
            } catch (ExpiredJwtException e) {
                return false;
            }
        }
        return false;
    }

    private Key getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Keys.hmacShaKeyFor(privateKey.getBytes(StandardCharsets.UTF_8));
    }
}
