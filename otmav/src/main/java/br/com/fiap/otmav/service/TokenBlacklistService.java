package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.token.TokenBlacklist;
import br.com.fiap.otmav.domain.token.TokenBlacklistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.Instant;

@Service
public class TokenBlacklistService {

    private static final Logger logger = LoggerFactory.getLogger(TokenBlacklistService.class);

    private final TokenBlacklistRepository repository;

    public TokenBlacklistService(TokenBlacklistRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void blacklistToken(String token, Instant expiresAt) {
        if (token == null || token.isBlank()) {
            logger.warn("Attempted to blacklist empty token — skipping");
            return;
        }

        try {
            if (repository.existsByToken(token)) {
                logger.info("Token already blacklisted (skipping): {}", token.substring(0, Math.min(token.length(), 32)));
                return;
            }

            TokenBlacklist entry = new TokenBlacklist(token, expiresAt == null ? Instant.now().plusSeconds(3600) : expiresAt);
            repository.saveAndFlush(entry); // force immediate INSERT
            logger.info("Blacklisted token (truncated) {} exp={}", token.substring(0, Math.min(token.length(), 32)), entry.getExpiresAt());
        } catch (Exception e) {
            logger.error("Failed to persist token blacklist entry", e);
            // don't rethrow - logout should not break the API flow
        }
    }

    public boolean isBlacklisted(String token) {
        if (token == null || token.isBlank()) return false;
        try {
            return repository.existsByToken(token);
        } catch (Exception e) {
            logger.error("Error checking blacklist for token", e);
            return false;
        }
    }

    // ... optional cleanupExpired method remains ...
}
