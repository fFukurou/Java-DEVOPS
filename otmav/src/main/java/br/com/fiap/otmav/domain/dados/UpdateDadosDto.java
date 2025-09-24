package br.com.fiap.otmav.domain.dados;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateDadosDto(
        @Size(max = 13)
        String telefone,

        @Email
        @Size(max = 255)
        String email,

        @Size(max = 20)
        String senha,

        @Size(max = 150)
        String nome
) {}
