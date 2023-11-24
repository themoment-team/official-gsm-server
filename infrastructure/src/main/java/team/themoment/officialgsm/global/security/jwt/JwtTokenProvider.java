package team.themoment.officialgsm.global.security.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.common.exception.CustomHttpStatus;
import team.themoment.officialgsm.global.security.auth.AuthDetailsService;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Getter
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.accessSecret}")
    private String accessSecret;
    @Value("${jwt.refreshSecret}")
    private String refreshSecret;
    private final AuthDetailsService authDetailsService;
    private final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 120 * 1000L;
    private final long REFRESH_TOKEN_EXPIRE_TIME = ACCESS_TOKEN_EXPIRE_TIME * 12 * 21;

    @AllArgsConstructor
    public enum TokenType{
        ACCESS_TOKEN,
        REFRESH_TOKEN
    }

    private Key getSignInKey(String secretKey){
        byte[] bytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }

    private String generatedToken(String oauthId, String type, String secret, long expiredTime){
        final Claims claims = Jwts.claims();
        claims.put("oauthId", oauthId);
        claims.put("type", type);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiredTime))
                .signWith(getSignInKey(secret), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generatedAccessToken(String oauthId){
        return generatedToken(oauthId, TokenType.ACCESS_TOKEN.name(), accessSecret, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generatedRefreshToken(String oauthId){
        return generatedToken(oauthId, TokenType.REFRESH_TOKEN.name(), refreshSecret, REFRESH_TOKEN_EXPIRE_TIME);
    }

    private Claims getTokenBody(String token, String secret){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey(secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e){
            throw new CustomException("토큰이 만료되었습니다.", CustomHttpStatus.UNAUTHORIZED);
        } catch (JwtException e){
            throw new CustomException("검증되지 않은 토큰입니다.", CustomHttpStatus.UNAUTHORIZED);
        }
    }

    private Claims getRefreshTokenBody(String token, String secret){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey(secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e){
            throw new CustomException("리프레시 토큰이 만료되었습니다.", CustomHttpStatus.UNAUTHORIZED);
        } catch (JwtException e){
            throw new CustomException("검증되지 않은 리프레시 토큰입니다.", CustomHttpStatus.UNAUTHORIZED);
        }
    }

    public UsernamePasswordAuthenticationToken authentication(String token){
        UserDetails userDetails = authDetailsService.loadUserByUsername(getTokenOauthId(token, accessSecret));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getTokenOauthId(String token, String secret){
        return getTokenBody(token, secret).get("oauthId", String.class);
    }

    public String getRefreshTokenOauthId(String token, String secret){
        return getRefreshTokenBody(token, secret).get("oauthId", String.class);
    }

    public long getExpiredAtAccessTokenToLong(){
        return ACCESS_TOKEN_EXPIRE_TIME/1000L;
    }

    public boolean isExpiredToken(String token, String secret) {
        final Date expiration = getTokenBody(token, secret).getExpiration();
        return expiration.before(new Date());
    }

    public boolean isValidToken(String token, String secret) {
        return isExpiredToken(token, secret);
    }

}
