package br.com.fiap.otmav.controller;

import br.com.fiap.otmav.domain.funcionario.RegisterFuncionarioDto;
import br.com.fiap.otmav.domain.funcionario.ReadFuncionarioDto;
import br.com.fiap.otmav.infra.security.LoginDto;
import br.com.fiap.otmav.infra.security.TokenResponse;
import br.com.fiap.otmav.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private FuncionarioService funcionarioService;

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
}
