package br.com.fiap.otmav.domain.token;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "token_blacklist")
@Getter
@Setter
@NoArgsConstructor
public class TokenBlacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token", nullable = false)
    private Long id;

    @Column(name = "token", length = 2000, nullable = false, unique = true)
    private String token;

    // store expiration to allow cleanup of old rows
    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    public TokenBlacklist(String token, Instant expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }
}
