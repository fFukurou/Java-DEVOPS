package br.com.fiap.otmav.controller;

import br.com.fiap.otmav.domain.funcionario.RegisterFuncionarioDto;
import br.com.fiap.otmav.domain.funcionario.ReadFuncionarioDto;
import br.com.fiap.otmav.infra.security.LoginDto;
import br.com.fiap.otmav.infra.security.TokenResponse;
import br.com.fiap.otmav.service.FuncionarioService;
import br.com.fiap.otmav.service.TokenBlacklistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.fiap.otmav.service.TokenBlacklistService;
import br.com.fiap.otmav.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private FuncionarioService funcionarioService;
    private final TokenService tokenService;
    private final TokenBlacklistService blacklistService;

    public AuthController(TokenService tokenService, TokenBlacklistService blacklistService) {
        this.tokenService = tokenService;
        this.blacklistService = blacklistService;
    }

    @Operation(summary = "Authenticate and receive JWT token")
    @ApiResponse(responseCode = "200", description = "Returns token")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginDto dto) {
        return funcionarioService.login(dto);
    }

    @Operation(summary = "Register new Funcionario (creates Dados and Funcionario in one transaction)")
    @ApiResponse(responseCode = "201", description = "Funcionario registered and Dados created")
    @PostMapping("/register")
    public ResponseEntity<ReadFuncionarioDto> register(
            @RequestBody @Valid RegisterFuncionarioDto dto,
            UriComponentsBuilder uriBuilder) {
        ReadFuncionarioDto created = funcionarioService.register(dto);
        URI uri = uriBuilder.path("/api/funcionarios/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = recoverToken(request);
        if (token == null) {
            logger.warn("Logout requested without Authorization header");
            // also try cookie fallback (optional)
            token = recoverTokenFromCookie(request);
            if (token == null) {
                return ResponseEntity.status(401).build();
            }
        }

        Instant expiresAt = tokenService.getExpirationInstant(token);
        if (expiresAt == null) {
            expiresAt = Instant.now().plusSeconds(3600);
        }
        blacklistService.blacklistToken(token, expiresAt);

        // Invalidate server session (important — prevents session-based auth from persisting)
        var session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Clear security context in the current thread
        SecurityContextHolder.clearContext();

        // Remove JSESSIONID cookie (helps browser/Swagger UI not reuse old session)
        Cookie clearSession = new Cookie("JSESSIONID", "");
        clearSession.setPath("/");
        clearSession.setMaxAge(0);
        response.addCookie(clearSession);

        return ResponseEntity.noContent().build();
    }

    private String recoverTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie c : request.getCookies()) {
            if ("OTMAV_TOKEN".equals(c.getName()) && c.getValue() != null && !c.getValue().isBlank()) {
                return c.getValue().trim();
            }
        }
        return null;
    }

    private String recoverToken(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) return null;
        return header.substring(7).trim();
    }

}
