package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.funcionario.Funcionario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret:my-secret-key}")
    private String secret;

    /**
     * Token expiration in hours (configured in application.properties as api.security.token.expiration)
     */
    @Value("${api.security.token.expiration:2}")
    private Long expirationHours;

    public String generateToken(Funcionario funcionario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("otmav-api")
                    .withSubject(funcionario.getDados().getEmail())          // subject = email
                    .withClaim("id", funcionario.getId())
                    .withClaim("cargo", funcionario.getCargo())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error generating token", exception);
        }
    }

    /**
     * Validates token and returns the subject (email) if valid, otherwise returns null.
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("otmav-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(expirationHours).toInstant(ZoneOffset.of("-03:00"));
    }


    /**
     * Returns the token expiration instant if the token is valid, otherwise null.
     * Does not check blacklist here (we'll do that in SecurityFilter).
     */
    public Instant getExpirationInstant(String token) {
        if (token == null || token.isBlank()) return null;
        try {
            // decode WITHOUT verifying signature so we can always read claims (if present)
            DecodedJWT decoded = JWT.decode(token);
            Date exp = decoded.getExpiresAt();
            return exp == null ? null : exp.toInstant();
        } catch (JWTDecodeException e) {
            // token is malformed, log or return null
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
