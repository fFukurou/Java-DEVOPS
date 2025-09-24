package br.com.fiap.otmav.domain.dados;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateDadosDto(
        @NotBlank
        @Size(min = 11, max = 11)
        String cpf,

        @Size(max = 13)
        String telefone,

        @Email
        @Size(max = 255)
        String email,

        @Size(max = 200)
        String senha,

        @NotBlank
        @Size(max = 150)
        String nome
) {}
