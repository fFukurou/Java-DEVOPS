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

    @Operation(summary = "Recebe e autentica JWT Tokens")
    @ApiResponse(responseCode = "200", description = "Retorna Token")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginDto dto) {
        return funcionarioService.login(dto);
    }

    @Operation(summary = "Registra novo funcionario (Cria Dados e Funcionario ao mesmo tempo)")
    @ApiResponse(responseCode = "201", description = "Funcionario Registrado e Dados Criados")
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
            logger.warn("LOGOUT Requisitado sem o Header: Authorization");
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

        // INVALIDA A SESSÃO
        var session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // IMPORTANTE PARA NENHUMA TRANSAÇÃO/SESSÃO FICAR PRESA
        SecurityContextHolder.clearContext();

        // REMOVE O COOKIE DA JSESSIONID
        Cookie clearSession = new Cookie("JSESSIONID", "");
        clearSession.setPath("/");
        clearSession.setMaxAge(0);
        response.addCookie(clearSession);

        return ResponseEntity.noContent().build();
    }

    // RECUPERA O TOKEN 'OTMAV_TOKEN' DOS COOKIES
    private String recoverTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie c : request.getCookies()) {
            if ("OTMAV_TOKEN".equals(c.getName()) && c.getValue() != null && !c.getValue().isBlank()) {
                return c.getValue().trim();
            }
        }
        return null;
    }

    // PEGA O TOKEN DAS REQUISIÇÕES API (SÓ A PARTE IMPORTANTE)
    private String recoverToken(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) return null;
        return header.substring(7).trim();
    }

}
