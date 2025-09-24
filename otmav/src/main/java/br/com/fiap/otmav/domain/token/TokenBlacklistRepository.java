package br.com.fiap.otmav.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    boolean existsByToken(String token);
    Optional<TokenBlacklist> findByToken(String token);
}
