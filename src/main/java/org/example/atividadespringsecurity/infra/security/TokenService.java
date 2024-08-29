package org.example.atividadespringsecurity.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.example.atividadespringsecurity.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret")
    private String secret;
    private final BlacklistedTokenRepository blacklistedTokenRepository;

    TokenService(BlacklistedTokenRepository blacklistedTokenRepository) {
        this.blacklistedTokenRepository = blacklistedTokenRepository;
    }

    public String generateToken(User user) throws JWTCreationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer("appointments-api")
                .withSubject(user.getUsername())
                .withExpiresAt(this.generateExpirationDate())
                .sign(algorithm);
    }

    public String validateToken(String token) throws JWTVerificationException {
        if (this.isBlacklisted(token)) {
            throw new JWTVerificationException("Token is blacklisted");
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("appointments-api")
                .build()
                .verify(token)
                .getSubject();
    }

    public void blacklistToken(String token) {
        BlacklistedToken blacklistedToken = new BlacklistedToken();
        blacklistedToken.setToken(token);
        this.blacklistedTokenRepository.save(blacklistedToken);
    }

    private boolean isBlacklisted(String token) {
        return this.blacklistedTokenRepository.existsByToken(token);
    }

    public String recoverTokenFromRequest(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
